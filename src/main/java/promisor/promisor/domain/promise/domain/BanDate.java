package promisor.promisor.domain.promise.domain;

import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

public class BanDate extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promise_id")
    private Promise promise;

    private LocalDateTime date;

    @Column(length = 50)
    private String reason;

    private char dateStatus;
}
