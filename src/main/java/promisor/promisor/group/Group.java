package promisor.promisor.group;

import lombok.Getter;
import promisor.promisor.model.BaseEntity;
import promisor.promisor.place.Place;
import promisor.promisor.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Group 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class Group extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private User user;

    @Column(length = 10)
    private String groupName;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDateTime date;
}
