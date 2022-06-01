package promisor.promisor.domain.promise.domain;

import lombok.Getter;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Opinion extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promise_id")
    private Promise promise;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Place place;

    private char banStatus;
}
