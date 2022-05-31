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
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public RegisterPersonalBanDateResponse registerPersonal(String email, Date date) {

        Member member = getMember(email);
        PersonalBanDate pbd = new PersonalBanDate(member,date);
        personalBanDateRepository.save(pbd);
        return new RegisterPersonalBanDateResponse(pbd.getMember().getId(), pbd.getDate());
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
    public void editPersonalBanDateStatus(String email, PersonalBanDateStatusEditRequest request) {

        PersonalBanDate pdb = findById(request.getId());
        pdb.editPBDStatus(request.getStatus());
        List<TeamBanDate> pbdList = findByBanDateId(pdb.getId());

        for (TeamBanDate teamBanDate : pbdList) {
            teamBanDate.editTBDStatus(request.getStatus());
        }
    }

    public List<GetTeamCalendarResponse> getTeamCalendar(String email, Long teamId) {
        PageRequest pageRequest = PageRequest.of(0, 31, Sort.by(Sort.Direction.ASC, "date"));
        Slice<TeamBanDate> teamCalendarList = teamBanDateRepository.findAllByTeamId(teamId, pageRequest);

        return teamCalendarList.stream()
                .map(p -> new GetTeamCalendarResponse(p.getId(), p.getMember().getId(), p.getMember().getName(),
                        p.getPersonalBanDate().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    @Transactional
    public RegisterPersonalReasonResponse registerPersonalReason(String email, String date, String reason) {

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
        List<GetPersonalCalendarResponse> result = PersonalCalenderList.stream().
                map(p -> new GetPersonalCalendarResponse(p.getId(), p.getMember().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
        return result;
    }

    public List<GetPersonalReasonResponse> getPersonalReason(String email) {

        Member member = getMember(email);
        List<PersonalBanDateReason> pbdrList = personalBanDateReasonRepository.findAllByMember(member.getId());
        List<GetPersonalReasonResponse> result = pbdrList.stream()
                .map(p-> new GetPersonalReasonResponse(p.getId(),p.getPersonalBanDate().getId(),p.getPersonalBanDate().getDate(), p.getReason()))
                .collect(Collectors.toList());
        return result;
    }
}
