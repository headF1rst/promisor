package promisor.promisor.domain.team.dto;

import lombok.Data;
import promisor.promisor.domain.team.domain.Team;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
public class SearchGroupResponse {

    private Long groupId;
    private String groupName;
    private String imageURL;
    private List<GroupMemberDto> groupMembers;

    public SearchGroupResponse(Long groupId, String groupName, String imageURL, List<GroupMemberDto> groupMembers) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.imageURL = imageURL;
        this.groupMembers = groupMembers;
    }

    public SearchGroupResponse(Team team) {
        this.groupId = team.getId();
        this.groupName = team.getGroupName();
        this.imageURL = team.getImageUrl();
        this.groupMembers = team.getTeamMembers().stream()
                .map(teamMembers -> new GroupMemberDto(teamMembers))
                .collect(toList());
    }
}
