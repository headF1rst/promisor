package promisor.promisor.domain.calendar.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.team.domain.Team;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "team_calendar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamCalendar extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "personal_id")
    private PersonalCalendar personalCalendar;

    private LocalDate date;

    @Column(name="date_status", length = 10, columnDefinition = "varchar(10) default 'IMPOSSIBLE'")
    @Enumerated(EnumType.STRING)
    private DateStatusType dateStatus;

    public TeamCalendar(Team team, Member member, PersonalCalendar personalCalendar, String date, String status) {
        super("ACTIVE");
        this.team = team;
        this.member = member;
        this.personalCalendar = personalCalendar;
        this.date = LocalDate.parse(date);
        this.dateStatus = DateStatusType.valueOf(status);
    }
    public void modifyStatus(String status) {
        this.dateStatus = DateStatusType.valueOf(status);
    }
}
