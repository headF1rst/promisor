package promisor.promisor.domain.goal;

import lombok.Getter;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.group.domain.Group;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * Goal 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class Goal extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(length = 100)
    private String goal;

    private Boolean check;
}
