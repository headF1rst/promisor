package promisor.promisor.domain.place;

import lombok.Getter;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Place 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class Place extends BaseEntity {

    @Column(name = "place_name", length = 30)
    private String placeName;

    @Column(length = 100)
    private String location;
}
