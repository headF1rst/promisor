package promisor.promisor.domain.store.domain;

import lombok.Getter;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import java.math.BigDecimal;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class StoreReview extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(length = 30)
    private String title;

    @Column(length = 500)
    private String content;

    @Column(precision = 2, scale = 1)
    private BigDecimal score;

    @Lob
    private String imageUrl;
}
