package promisor.promisor.domain.bandate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalBanDateReason extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "personal_ban_date_id")
    private PersonalBanDate personalBanDate;

    @Column(length = 50)
    private String reason;

    public PersonalBanDateReason(PersonalBanDate pbd, String reason){
        super("ACTIVE");
        this.personalBanDate = pbd;
        this.reason = reason;
    }
}
