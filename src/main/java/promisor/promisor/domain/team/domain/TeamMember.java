package promisor.promisor.domain.team.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "GroupMember")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(length = 100)
    private String curLocate;

    private double latitude;

    private double longitude;

    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
    }

    public void editMyLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getTeamIdFromTeam() {
        return this.team.getId();
    }

    public String getGroupNameFromTeam() {
        return this.team.getGroupName();
    }

    public String getImageUrlFromTeam() {
        return this.team.getImageUrl();
    }

    public Set<TeamMember> getTeamMembersFromTeam() {
        return this.team.getTeamMembers();
    }
}
