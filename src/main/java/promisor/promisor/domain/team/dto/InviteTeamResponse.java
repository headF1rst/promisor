package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class InviteTeamResponse {
    private final Long groupId;

    public InviteTeamResponse(Long groupId) {
        this.groupId = groupId;
    }
}
