package promisor.promisor.domain.store.domain;

import lombok.Getter;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.*;

/**
 * StoreImage 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class StoreImage extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Store store;

    @Lob
    private String imageUrl;
}
