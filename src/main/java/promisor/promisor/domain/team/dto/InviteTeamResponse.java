package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class InviteTeamResponse {
    private Long memberId;
    private Long groupId;

    public InviteTeamResponse(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }
}
