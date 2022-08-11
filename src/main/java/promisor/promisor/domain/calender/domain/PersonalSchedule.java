package promisor.promisor.domain.calender.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalSchedule extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "personal_calender_id")
    private
    PersonalCalender personalCalender;

    @Column(length = 50)
    private String reason;

    public PersonalSchedule(PersonalCalender personalCalender, String reason){
        super("ACTIVE");
        this.personalCalender = personalCalender;
        this.reason = reason;
    }
}
