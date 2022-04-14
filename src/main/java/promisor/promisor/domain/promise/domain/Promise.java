package promisor.promisor.domain.promise.domain;

import lombok.Getter;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Promise extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "groups_id")
    private Team team;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(length = 50)
    private String promiseName;

    private LocalDateTime date;
}
