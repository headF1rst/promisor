package promisor.promisor.domain.team.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.place.domain.Place;
import promisor.promisor.domain.member.domain.Member;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long teamLeaderId;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private final Set<TeamMember> teamMembers = new HashSet<>();

    @Column(length = 30)
    private String teamName;

    @Lob
    private String imageUrl;

    @Embedded
    private Place place;

    public Team(Long teamLeaderId, String groupName) {
        super("ACTIVE");
        this.teamLeaderId = teamLeaderId;
        this.teamName = groupName;
    }

    public void changeGroupName(String groupName) {
        this.teamName = groupName;
    }

    public void changeLeader(Member member){
        this.teamLeaderId = member.getId();
    }

    public void addTeam(TeamMember teamMember) {
        teamMembers.add(teamMember);
    }

    public boolean memberNotBelongsToTeam(Long id) {
        for (TeamMember teamMember : teamMembers) {
            return teamMember.getMemberId() == id;
        }
        return false;
    }

    public List<Double> calculateMidPoint() {
        double avgLatitude = 0;
        double avgLongitude = 0;

        for (TeamMember teamMember : teamMembers) {
            avgLatitude += teamMember.getLatitude();
            avgLongitude += teamMember.getLongitude();
        }
        avgLatitude /= teamMembers.size();
        avgLongitude /= teamMembers.size();
        return List.of(avgLatitude, avgLongitude);
    }
}
