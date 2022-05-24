package promisor.promisor.domain.bandate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;


import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "personal_ban_date")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalBanDate extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Date date;

    @Column(length = 50)
    private String reason;

    @Column(name="date_status", length = 10)
    private String dateStatus;

    public PersonalBanDate(Member member, Date date, String reason){
        super("ACTIVE");
        this.member = member;
        this.date = date;
        this.reason = reason;
        this.dateStatus = "IMPOSSIBLE";
    }

    public void editPBDStatus(String status) {
        this.dateStatus = status;
    }
}
