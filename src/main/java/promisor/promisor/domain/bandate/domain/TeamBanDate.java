package promisor.promisor.domain.bandate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.team.domain.Team;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "team_ban_date")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamBanDate extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "personal_id")
    private PersonalBanDate personalBanDate;

    private Date date;

    @Column(length = 10)
    private String dateStatus;

    public TeamBanDate(Team team, Member member, PersonalBanDate pbd, Date date, String dateStatus){
        this.team = team;
        this.member = member;
        this.personalBanDate = pbd;
        this.date = date;
        this.dateStatus = dateStatus;
    }
    public void editTBDStatus(String status) {
        this.dateStatus=status;
    }
}