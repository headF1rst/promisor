package promisor.promisor.domain.bandate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    @Value(value = "IMPOSSIBLE")
    private String dateStatus;

    public PersonalBanDate(Member member, Date date, String reason){
        this.member = member;
        this.date = date;
        this.reason = reason;
    }

    public void prePersist() {
        this.dateStatus = this.dateStatus == null ? "IMPOSSIBLE" : this.dateStatus;
    }
}
