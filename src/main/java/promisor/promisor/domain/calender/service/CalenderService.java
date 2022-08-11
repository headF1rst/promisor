package promisor.promisor.domain.calender.service;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.calender.dao.PersonalScheduleRepository;
import promisor.promisor.domain.calender.dao.PersonalCalenderRepository;
import promisor.promisor.domain.calender.dao.TeamCalenderRepository;
import promisor.promisor.domain.calender.domain.DateStatusType;
import promisor.promisor.domain.calender.domain.PersonalCalender;
import promisor.promisor.domain.calender.domain.PersonalSchedule;
import promisor.promisor.domain.calender.domain.TeamCalender;
import promisor.promisor.domain.calender.dto.*;
import promisor.promisor.domain.calender.exception.*;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberNotFoundException;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;

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
public class CalenderService {
    private final PersonalCalenderRepository personalCalenderRepository;
    private final PersonalScheduleRepository personalScheduleRepository;
    private final MemberRepository memberRepository;
    private final TeamCalenderRepository teamCalenderRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public RegisterPersonalCalenderResponse registerPersonal(String email, String date, String status) {
        Assert.notNull(date, "date 값이 null이어선 안됩니다.");

        Member member = getMember(email);
        PersonalCalender personalCalender = personalCalenderRepository.findPersonalBanDateByMemberAndDate(member, date);
        List<TeamCalender> tbd = teamCalenderRepository.findAllByMemberAndDate(member, date);
        List<Team> teams = teamRepository.findAllByMember(member);
        if (personalCalender != null) {
            personalCalender.modifyStatus(status);
            return getRegisterPersonalBanDateResponse(date, status, member, personalCalender, tbd, teams);
        } else {
            PersonalCalender newPersonalCalender = new PersonalCalender(member, date, status);
            personalCalenderRepository.save(newPersonalCalender);
            return getRegisterPersonalBanDateResponse(date, status, member, newPersonalCalender, tbd, teams);
        }
    }

    private RegisterPersonalCalenderResponse getRegisterPersonalBanDateResponse(String date, String status, Member member,
                                                                                PersonalCalender personalCalender,
                                                                                List<TeamCalender> teamCalenders,
                                                                                List<Team> teams) {
        if (teamCalenders.isEmpty()) {
            for (Team team : teams) {
                TeamCalender new_tbd = new TeamCalender(team, member, personalCalender, date, status);
                teamCalenderRepository.save(new_tbd);
            }
        } else {
            reflectToTeam(teamCalenders,status);
        }
        return new RegisterPersonalCalenderResponse(member.getId(), personalCalender.getDate(),
                personalCalender.getDateStatus());
    }

    private void reflectToTeam(List<TeamCalender> tbd, String status){
        for (TeamCalender teamCalender : tbd) {
            teamCalender.modifyStatus(status);
        }
    }

    private Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public ModifyStatusResponse modifyStatus(String email, String date, String status) {
        Assert.notNull(date, "date는 null이어선 안됩니다.");
        Assert.notNull(status, "status는 null이어선 안됩니다.");

        Member member = getMember(email);
        PersonalCalender personalCalender = personalCalenderRepository.findPersonalBanDateByMemberAndDate(member, date);
        if (personalCalender == null){
            throw new WrongAccess();
        }
        personalCalender.modifyStatus(status);
        List<TeamCalender> teamCalenders = teamCalenderRepository.findAllByMemberAndDate(member, date);
        if (!teamCalenders.isEmpty()){
            for (TeamCalender teamCalender : teamCalenders) {
                teamCalender.modifyStatus(status);
            }
        }
        return new ModifyStatusResponse(member.getId(), personalCalender.getDate(), personalCalender.getDateStatus());
    }

    @Transactional
    public List<GetTeamCalendarResponse> getTeamCalendarDetail(String email, Long teamId, String date) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        Slice<TeamCalender> teamCalendarList = teamCalenderRepository.findAllByTeamIdAndDate(teamId, inputDate, pageRequest);
        return teamCalendarList.stream()
                .map(p -> new GetTeamCalendarResponse(p.getId(), p.getMember().getId(), p.getMember().getName(),
                        p.getPersonalCalender().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    @Transactional
    public RegisterPersonalScheduleResponse registerPersonalSchedule(String email, String date, String reason) {
        Member member = getMember(email);
        PersonalCalender personalCalender = personalCalenderRepository.findPersonalBanDateByMemberAndDate(member, date);
        if (personalCalender == null){
            throw new WrongAccess();
        }
        PersonalSchedule pbd_reason = new PersonalSchedule(personalCalender, reason);
        personalScheduleRepository.save(pbd_reason);
        return new RegisterPersonalScheduleResponse(pbd_reason.getId(), pbd_reason.getPersonalCalender().getId(),
                pbd_reason.getReason());
    }

    public List<GetPersonalCalendarResponse> getPersonalCalendar(String email) {
        PageRequest pageRequest = PageRequest.of(0, 31, Sort.by(Sort.Direction.ASC, "date"));
        Member member = getMember(email);
        Slice<PersonalCalender> PersonalCalenderList = personalCalenderRepository.findAllByMemberId(member.getId(), pageRequest);
        return PersonalCalenderList.stream().
                map(p -> new GetPersonalCalendarResponse(p.getId(), p.getMember().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    public GetPersonalScheduleResponse getPersonalSchedule(String email, String date) {
        Member member = getMember(email);
        PersonalCalender personalCalender = personalCalenderRepository.findPersonalBanDateByMemberAndDate(member, date);
        if (personalCalender == null) {
            return new GetPersonalScheduleResponse("POSSIBLE");
        } else {
            List<PersonalSchedule> schedules = personalScheduleRepository.findAllByPersonalCalender(personalCalender);
            List<String> reasons = schedules.stream().map(PersonalSchedule::getReason).collect(Collectors.toList());
            return new GetPersonalScheduleResponse(personalCalender.getDateStatus(), reasons);
        }
    }

    public List<GetTeamCalendarStatusResponse> getTeamCalendarStatus(String email, Long teamId, String yearMonth) {
        List<GetTeamCalendarStatusResponse> result = new ArrayList<>();
        DateStatusType impossible = DateStatusType.valueOf("IMPOSSIBLE");
        DateStatusType uncertain = DateStatusType.valueOf("UNCERTAIN");
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
            List<TeamCalender> tbdList = teamCalenderRepository.findAllByTeamIdAndDates(teamId, date);
            String check = "POSSIBLE";
            for (TeamCalender teamCalender : tbdList) {
                System.out.println(teamCalender.getDateStatus());
                if (teamCalender.getDateStatus().equals(impossible)) {
                    check = "IMPOSSIBLE";
                    break;
                }
                if (teamCalender.getDateStatus().equals(uncertain)) {
                    check = "UNCERTAIN";
                }
            }
            result.add(new GetTeamCalendarStatusResponse(date, check));
        }
        return result;
    }
}
