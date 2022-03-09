package promisor.promisor.domain.chat;

import lombok.Getter;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.member.Member;
import promisor.promisor.domain.team.Team;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * Chat 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class Chat extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;
}
