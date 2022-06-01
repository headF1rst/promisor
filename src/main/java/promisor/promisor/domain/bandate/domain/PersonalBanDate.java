package promisor.promisor.domain.bandate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;


import java.time.LocalDate;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class PersonalBanDate extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate date;

    @Column(name="date_status", length = 10, columnDefinition = "varchar(10) default 'IMPOSSIBLE'")
    @Enumerated(EnumType.STRING)
    private DateStatusType dateStatus;


    public PersonalBanDate(Member member, String date, String dateStatus) {
        super("ACTIVE");
        this.member = member;
        this.date = LocalDate.parse(date);
        if (dateStatus==null) {
            this.dateStatus = DateStatusType.valueOf("IMPOSSIBLE");
        }
        else {
            this.dateStatus = DateStatusType.valueOf(dateStatus);
        }
    }

    public void editPBDStatus(String status) {
        this.dateStatus = DateStatusType.valueOf(status);
    }
}
