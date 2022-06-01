package promisor.promisor.domain.store.domain;

import lombok.Getter;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * Store 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class Store extends BaseEntity {

    @Embedded
    private Place place;

    @Column(length = 50)
    private String storeName;

    @Column(length = 200)
    private String storeInfo;

    @Column(length = 100)
    private String location;
}
