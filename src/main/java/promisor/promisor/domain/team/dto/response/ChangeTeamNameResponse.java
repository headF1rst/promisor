package promisor.promisor.domain.team.dto.response;

import lombok.Getter;

@Getter
public class ChangeTeamNameResponse {
    private final String groupName;

    public ChangeTeamNameResponse(String groupName){
        this.groupName=groupName;
    }
}
