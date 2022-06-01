package promisor.promisor.domain.bandate.service;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Transactional
    public RegisterPersonalBanDateResponse registerPersonal(String email, String date, String status) {
        if (date == null){
            throw new DateEmptyException();
        }
        Member member = getMember(email);
        PersonalBanDate pbd = personalBanDateRepository.getPersonalBanDateByMemberAndDate(member, date);
        if (pbd != null){
            pbd.editPBDStatus(status);
            return new RegisterPersonalBanDateResponse(member.getId(), pbd.getDate(), pbd.getDateStatus());
        }else{
            PersonalBanDate new_pbd = new PersonalBanDate(member, date, status);
            personalBanDateRepository.save(new_pbd);
            return new RegisterPersonalBanDateResponse(member.getId(), new_pbd.getDate(),new_pbd.getDateStatus());
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

    public List<GetTeamCalendarStatusResponse> getTeamCalendarStatus(String email, Long teamId, String yearMonth) {
        List<GetTeamCalendarStatusResponse> result = new ArrayList<>();
        String impossible = "IMPOSSIBLE";
        String uncertain = "UNCERTAIN";
        Integer year = Integer.parseInt(yearMonth.substring(0, 4));
        Integer month = Integer.parseInt(yearMonth.substring(5));
        List<Integer> monthSize = Arrays.asList(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        monthSize.set(2, (year%4==0&&year%100!=0)||year%400==0?29:28);
        for (int day=1; day<=monthSize.get(month); day++) {
            String dayString = null;
            if (day<10) {
                dayString = "0"+String.valueOf(day);
            }
            else {
                dayString = String.valueOf(day);
            }
            LocalDate date = LocalDate.parse(yearMonth + "-" + dayString, DateTimeFormatter.ISO_DATE);
            List<TeamBanDate> tbdList = teamBanDateRepository.findAllByTeamIdAndDates(teamId, date);
            String check = "POSSIBLE";
            for (int i = 0; i < tbdList.size(); i++) {
                if (tbdList.get(i).getDateStatus().equals(impossible)) {
                    check = "IMPOSSIBLE";
                    break;
                }
                if (tbdList.get(i).getDateStatus().equals(uncertain)) {
                    check = "UNCERTAIN";
                }
            }
            result.add(new GetTeamCalendarStatusResponse(date, check));
        }
        return result;
    }
}
