package promisor.promisor.domain.calendar.domain;

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

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "personal_calendar_id")
    private
    PersonalCalendar personalCalendar;

    @Column(length = 50)
    private String reason;

    public PersonalSchedule(PersonalCalendar personalCalendar, String reason){
        super("ACTIVE");
        this.personalCalendar = personalCalendar;
        this.reason = reason;
    }
}
