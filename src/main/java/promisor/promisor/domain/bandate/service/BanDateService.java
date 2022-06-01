package promisor.promisor.domain.bandate.service;

import com.fasterxml.jackson.databind.SequenceWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.dao.PersonalBanDateReasonRepository;
import promisor.promisor.domain.bandate.dao.PersonalBanDateRepository;
import promisor.promisor.domain.bandate.dao.TeamBanDateRepository;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.bandate.domain.PersonalBanDateReason;
import promisor.promisor.domain.bandate.domain.TeamBanDate;
import promisor.promisor.domain.bandate.dto.*;
import promisor.promisor.domain.bandate.exception.*;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberNotFoundException;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BanDateService {
    private final PersonalBanDateRepository personalBanDateRepository;
    private final PersonalBanDateReasonRepository personalBanDateReasonRepository;
    private final MemberRepository memberRepository;
    private final TeamBanDateRepository teamBanDateRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public RegisterPersonalBanDateResponse registerPersonal(String email, String date, String status) {
        if (date == null){
            throw new DateEmptyException();
        }
        Member member = getMember(email);
        PersonalBanDate pbd = personalBanDateRepository.getPersonalBanDateByMemberAndDate(member, date);
        List<TeamBanDate> tbd = teamBanDateRepository.findAllByMemberAndDate(member, date);
        List<Team> teams = teamRepository.findAllByMember(member);
        if (pbd != null){
            pbd.editPBDStatus(status);
            return getRegisterPersonalBanDateResponse(date, status, member, pbd, tbd, teams);
        }else{
            PersonalBanDate new_pbd = new PersonalBanDate(member, date, status);
            personalBanDateRepository.save(new_pbd);
            return getRegisterPersonalBanDateResponse(date, status, member, new_pbd, tbd, teams);
        }
    }

    private RegisterPersonalBanDateResponse getRegisterPersonalBanDateResponse(String date, String status, Member member, PersonalBanDate pbd, List<TeamBanDate> tbd, List<Team> teams) {
        if (tbd.isEmpty()){
            for(int i=0; i<teams.size(); i++){
                TeamBanDate new_tbd = new TeamBanDate(teams.get(i), member, pbd, date, status);
                System.out.println(new_tbd);
                teamBanDateRepository.save(new_tbd);
            }
        }else{
            reflectToTeam(tbd,status);
        }
        return new RegisterPersonalBanDateResponse(member.getId(), pbd.getDate(), pbd.getDateStatus());
    }

    private void reflectToTeam(List<TeamBanDate> tbd, String status){
        for(int i=0; i<tbd.size(); i++){
            if(Objects.equals(tbd.get(i).getDateStatus(), "IMPOSSIBLE")){
                continue;
            }
            if (Objects.equals(status, "IMPOSSIBLE")){
                tbd.get(i).editTBDStatus(status);
            }
            else if (Objects.equals(status, "UNCERTAIN")){
                if(Objects.equals(tbd.get(i).getDateStatus(), "POSSIBLE")){
                    tbd.get(i).editTBDStatus(status);
                }
            }
        }
    }

    private Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    private PersonalBanDate findById(Long id) {
        return personalBanDateRepository.findById(id).orElseThrow();
    }

    private List<TeamBanDate> findByBanDateId(Long id) {
        return teamBanDateRepository.getByBanDateId(id).orElseThrow();
    }

    @Transactional
    public ModifyStatusResponse editPersonalBanDateStatus(String email, String date, String status) {
        if (date == null){
            throw new DateEmptyException();
        }
        if (status == null){
            throw new StatusEmptyException();
        }
        Member member = getMember(email);
        PersonalBanDate pbd = personalBanDateRepository.getPersonalBanDateByMemberAndDate(member, date);
        pbd.editPBDStatus(status);
        return new ModifyStatusResponse(member.getId(), pbd.getDate(), pbd.getDateStatus());
    }

    @Transactional
    public List<GetTeamCalendarResponse> getTeamCalendarDetail(String email, Long teamId, String date) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        Slice<TeamBanDate> teamCalendarList = teamBanDateRepository.findAllByTeamIdAndDate(teamId, inputDate, pageRequest);
        return teamCalendarList.stream()
                .map(p -> new GetTeamCalendarResponse(p.getId(), p.getMember().getId(), p.getMember().getName(),
                        p.getPersonalBanDate().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    @Transactional
    public RegisterPersonalReasonResponse registerPersonalReason(String email, String date, String reason) {

        if (date == null){
            throw new DateEmptyException();
        }
        if (reason == null){
            throw new ReasonEmptyException();
        }

        Member member = getMember(email);
        PersonalBanDate pbd = personalBanDateRepository.getPersonalBanDateByMemberAndDate(member, date);
        PersonalBanDateReason pbd_reason = new PersonalBanDateReason(pbd, reason);
        personalBanDateReasonRepository.save(pbd_reason);
        return new RegisterPersonalReasonResponse(pbd_reason.getId(), pbd_reason.getPersonalBanDate().getId(), pbd_reason.getReason());
    }

    public List<GetPersonalCalendarResponse> getPersonalCalendar(String email) {

        PageRequest pageRequest = PageRequest.of(0, 31, Sort.by(Sort.Direction.ASC, "date"));
        Member member = getMember(email);
        Slice<PersonalBanDate> PersonalCalenderList = personalBanDateRepository.findAllByMemberId(member.getId(), pageRequest);
        return PersonalCalenderList.stream().
                map(p -> new GetPersonalCalendarResponse(p.getId(), p.getMember().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    public GetPersonalReasonResponse getPersonalReason(String email, String date) {
        if (date == null){
            throw new DateEmptyException();
        }
        Member member = getMember(email);
        List<PersonalBanDateReason> pbdrList = personalBanDateReasonRepository.findAllByMember(member.getId());
        PersonalBanDate pbd = personalBanDateRepository.getPersonalBanDateByMemberAndDate(member, date);
        List<String> reasons = pbdrList.stream().map(p->p.getReason()).collect(Collectors.toList());
        return new GetPersonalReasonResponse(pbd.getDateStatus(), reasons);
    }
}
