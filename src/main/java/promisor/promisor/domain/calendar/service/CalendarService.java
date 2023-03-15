package promisor.promisor.domain.calendar.service;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.calendar.dao.PersonalScheduleRepository;
import promisor.promisor.domain.calendar.dao.PersonalCalendarRepository;
import promisor.promisor.domain.calendar.dao.TeamCalendarRepository;
import promisor.promisor.domain.calendar.domain.DateStatusType;
import promisor.promisor.domain.calendar.domain.PersonalCalendar;
import promisor.promisor.domain.calendar.domain.PersonalSchedule;
import promisor.promisor.domain.calendar.domain.TeamCalendar;
import promisor.promisor.domain.calendar.dto.*;
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
public class CalendarService {

    private final PersonalCalendarRepository personalCalendarRepository;
    private final PersonalScheduleRepository personalScheduleRepository;
    private final MemberRepository memberRepository;
    private final TeamCalendarRepository teamCalendarRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public RegisterPersonalCalendarResponse registerPersonalCalendar(String email, String date, String status) {
        Assert.notNull(date, "date 값이 null이어선 안됩니다.");

        Member member = getMember(email);
        PersonalCalendar personalCalendar = personalCalendarRepository.findPersonalCalendarByMemberAndDate(member, date);
        List<TeamCalendar> teamCalendars = teamCalendarRepository.findAllByMemberAndDate(member, date);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "teamName");
        List<Team> teams = teamRepository.findAllTeams(member, pageRequest);

        if (personalCalendar != null) {
            personalCalendar.modifyStatus(status);
            return getRegisterPersonalCalendarResponse(date, status, member, personalCalendar, teamCalendars, teams);
        }

        PersonalCalendar newPersonalCalendar = new PersonalCalendar(member, date, status);
        personalCalendarRepository.save(newPersonalCalendar);
        return getRegisterPersonalCalendarResponse(date, status, member, newPersonalCalendar, teamCalendars, teams);
    }

    @Transactional
    public ModifyStatusResponse modifyPersonalCalendarStatus(String email, String date, String status) {
        Assert.notNull(date, "date는 null이어선 안됩니다.");
        Assert.notNull(status, "status는 null이어선 안됩니다.");

        Member member = getMember(email);
        PersonalCalendar personalCalendar = personalCalendarRepository.findPersonalCalendarByMemberAndDate(member, date);
        Assert.notNull(personalCalendar, "등록된 일정이 없습니다.");
        personalCalendar.modifyStatus(status);

        List<TeamCalendar> teamCalendars = teamCalendarRepository.findAllByMemberAndDate(member, date);
        if (!teamCalendars.isEmpty()) {
            reflectToTeam(teamCalendars, status);
        }
        return new ModifyStatusResponse(member.getId(), personalCalendar.getDate(), personalCalendar.getDateStatus());
    }

    public List<GetTeamCalendarResponse> getTeamCalendarDetail(String email, Long teamId, String date) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        Slice<TeamCalendar> teamCalendarList = teamCalendarRepository.findAllByTeamIdAndDate(teamId, inputDate, pageRequest);
        return teamCalendarList.stream()
                .map(p -> new GetTeamCalendarResponse(p.getId(), p.getMember().getId(), p.getMember().getName(),
                        p.getPersonalCalendar().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    @Transactional
    public RegisterPersonalScheduleResponse registerPersonalSchedule(String email, String date, String reason) {
        Member member = getMember(email);
        PersonalCalendar personalCalendar = personalCalendarRepository.findPersonalCalendarByMemberAndDate(member, date);
        Assert.notNull(personalCalendar, "등록된 일정이 없습니다.");

        PersonalSchedule personalSchedule = new PersonalSchedule(personalCalendar, reason);
        personalScheduleRepository.save(personalSchedule);
        return new RegisterPersonalScheduleResponse(personalSchedule.getId(), personalSchedule.getPersonalCalendar().getId(),
                personalSchedule.getReason());
    }

    public List<GetPersonalCalendarResponse> getPersonalCalendar(String email) {
        PageRequest pageRequest = PageRequest.of(0, 31, Sort.by(Sort.Direction.ASC, "date"));
        Member member = getMember(email);
        Slice<PersonalCalendar> PersonalCalendarList = personalCalendarRepository.findAllByMemberId(member.getId(), pageRequest);
        return PersonalCalendarList.stream().
                map(p -> new GetPersonalCalendarResponse(p.getId(), p.getMember().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
    }

    public GetPersonalScheduleResponse getPersonalSchedule(String email, String date) {
        Member member = getMember(email);
        PersonalCalendar personalCalendar = personalCalendarRepository.findPersonalCalendarByMemberAndDate(member, date);
        if (personalCalendar == null) {
            return new GetPersonalScheduleResponse("POSSIBLE");
        } else {
            List<PersonalSchedule> schedules = personalScheduleRepository.findAllByPersonalCalendar(personalCalendar);
            List<String> reasons = schedules.stream().map(PersonalSchedule::getReason).collect(Collectors.toList());
            return new GetPersonalScheduleResponse(personalCalendar.getDateStatus(), reasons);
        }
    }

    public List<GetTeamCalendarStatusResponse> getTeamCalendarStatusByYearMonth(String email, Long teamId, String yearMonth) {
        List<GetTeamCalendarStatusResponse> result = new ArrayList<>();
        DateStatusType impossible = DateStatusType.valueOf("IMPOSSIBLE");
        DateStatusType uncertain = DateStatusType.valueOf("UNCERTAIN");
        Integer year = Integer.parseInt(yearMonth.substring(0, 4));
        Integer month = Integer.parseInt(yearMonth.substring(5));
        List<Integer> monthSize = Arrays.asList(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        monthSize.set(2, (year % 4 == 0 && year % 100 != 0) || year % 400 == 0 ? 29 : 28);

        for (int day = 1; day <= monthSize.get(month); day++) {
            String dayString = String.valueOf(day);
            if (day < 10) {
                dayString = "0" + day;
            }
            LocalDate date = LocalDate.parse(yearMonth + "-" + dayString, DateTimeFormatter.ISO_DATE);
            List<TeamCalendar> teamCalendarList = teamCalendarRepository.findAllByTeamIdAndDates(teamId, date);
            DateStatusType check = DateStatusType.POSSIBLE;
            for (TeamCalendar teamCalendar : teamCalendarList) {
                if (teamCalendar.isDate(impossible)) {
                    check = DateStatusType.IMPOSSIBLE;
                    break;
                }
                if (teamCalendar.isDate(uncertain)) {
                    check = DateStatusType.UNCERTAIN;
                }
            }
            result.add(new GetTeamCalendarStatusResponse(date, check));
        }
        return result;
    }

    private RegisterPersonalCalendarResponse getRegisterPersonalCalendarResponse(String date, String status, Member member,
                                                                                 PersonalCalendar personalCalendar,
                                                                                 List<TeamCalendar> teamCalendars,
                                                                                 List<Team> teams) {
        if (teamCalendars.isEmpty()) {
            saveTeamCalendar(date, status, member, personalCalendar, teams);
        } else {
            reflectToTeam(teamCalendars, status);
        }
        return new RegisterPersonalCalendarResponse(member.getId(), personalCalendar.getDate(),
                personalCalendar.getDateStatus());
    }

    private void saveTeamCalendar(String date, String status, Member member, PersonalCalendar personalCalendar, List<Team> teams) {
        for (Team team : teams) {
            TeamCalendar teamCalendar = new TeamCalendar(team, member, personalCalendar, date, status);
            teamCalendarRepository.save(teamCalendar);
        }
    }

    private void reflectToTeam(List<TeamCalendar> teamCalendars, String status) {
        for (TeamCalendar teamCalendar : teamCalendars) {
            teamCalendar.modifyStatus(status);
        }
    }

    private Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
}
