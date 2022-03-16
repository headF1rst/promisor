package promisor.promisor.domain.team.domain;

import lombok.Getter;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Invite extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Team team;

    private char accept;
}
