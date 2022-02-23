package promisor.promisor.domain.chat;

import lombok.Getter;
import promisor.promisor.domain.group.Group;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.user.User;

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
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;
}
