package promisor.promisor.domain.place.domain;

import lombok.Getter;
import org.springframework.security.core.parameters.P;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * Place 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Embeddable
@Getter
public class Place {

    @Column(length = 100)
    private String name;

    private Double latitude;
    private Double longitude;

    private Place(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private Place(String name) {
        this.name = name;
    }

    public static Place from(String name) {
        return new Place(name);
    }

    public void changeLocation(String name) {
        this.name = name;
    }
}
