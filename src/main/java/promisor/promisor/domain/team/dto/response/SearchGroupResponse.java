package promisor.promisor.domain.team.dto.response;

import lombok.Data;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
public class SearchGroupResponse {

    private Long groupId;
    private String groupName;
    private String imageURL;
    private List<String> memberNames;

    public SearchGroupResponse(Long groupId, String groupName, String imageURL, Set<TeamMember> groupMembers) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.imageURL = imageURL;
        this.memberNames = groupMembers.stream()
                .map(teamMembers -> teamMembers.getMember().getName())
                .sorted()
                .collect(toList());
    }
}
