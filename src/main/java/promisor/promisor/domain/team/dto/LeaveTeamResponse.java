package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class LeaveTeamResponse {
    private final Long memberId;
    private final Long groupId;

    public LeaveTeamResponse(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }
}
