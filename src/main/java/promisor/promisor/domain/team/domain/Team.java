package promisor.promisor.domain.team.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;
import promisor.promisor.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Group 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
@Table(name="team", schema = "promisorDB-dev")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 10)
    private String groupName;

    @Lob
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDateTime date;


    public Team(Member member, String groupName) {
        this.member = member;
        this.groupName = groupName;
    }

    public void changeGroupName(String groupName) {
        this.groupName = groupName;
    }
}
