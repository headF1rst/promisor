package promisor.promisor.domain.calender.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;


import java.time.LocalDate;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class PersonalCalender extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate date;

    @Column(name="date_status", length = 10, columnDefinition = "varchar(10) default 'IMPOSSIBLE'")
    @Enumerated(EnumType.STRING)
    private DateStatusType dateStatus;


    public PersonalCalender(Member member, String date, String dateStatus) {
        super("ACTIVE");
        this.member = member;
        this.date = LocalDate.parse(date);
        this.dateStatus = DateStatusType.valueOf(Objects.requireNonNullElse(dateStatus, "IMPOSSIBLE"));
    }

    public void modifyStatus(String status) {
        this.dateStatus = DateStatusType.valueOf(status);
    }
}
