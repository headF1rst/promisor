package promisor.promisor.domain.team.domain;

import lombok.Getter;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "GroupMember")
public class TeamMember extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Team team;

    @Column(length = 100)
    private String curLocate;

    private char arrived;
}
