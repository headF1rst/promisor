package promisor.promisor.domain.team.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;
import promisor.promisor.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Group 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 30)
    private String groupName;

    @Lob
    private String imageUrl;

    @Embedded
    private Place place;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private final Set<TeamMember> teamMembers = new HashSet<>();

    public Team(Member member, String groupName) {
        super("ACTIVE");
        this.member = member;
        this.groupName = groupName;
    }

    public void changeGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void changeLeader(Member member){
        this.member = member;
    }

    public void addTeam(TeamMember teamMember) {
        teamMembers.add(teamMember);
    }
}
