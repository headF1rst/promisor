package promisor.promisor.domain.promise.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promise extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    private Place place;

    @Column(length = 50)
    private String promiseName;

    private LocalDateTime date;

    private Promise(String status, Team team, Place place, String promiseName, LocalDateTime date) {
        super(status);
        this.team = team;
        this.place = place;
        this.promiseName = promiseName;
        this.date = date;
    }

    private Promise(String status, Team team, String promiseName) {
        super(status);
        this.team = team;
        this.promiseName = promiseName;
    }

    public static Promise of(String status, Team team, String promiseName) {
        return new Promise(status, team, promiseName);
    }

    public void changePromiseContent(String promiseName, String location){
        if (promiseName != null) {
            this.promiseName = promiseName;
        }

        if (location != null) {
            if (this.place == null) {
                this.place = Place.from(location);
            } else {
                this.place.changeLocation(location);
            }
        }
    }

    public String getPromiseLocation() {
        if (this.place == null) {
            return null;
        }
        return place.getName();
    }
}
