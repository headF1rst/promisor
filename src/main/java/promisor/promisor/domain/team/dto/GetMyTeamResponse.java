package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class GetMyTeamResponse {

    private final Long id;
    private final String groupName;
    private final String imageUrl;

    public GetMyTeamResponse(Long id, String groupName, String imageUrl){
        this.id=id;
        this.groupName=groupName;
        this.imageUrl=imageUrl;
    }
}
