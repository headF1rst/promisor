package promisor.promisor.domain.team.dto;

public class LeaveGroupResponse {
    private final Long memberId;
    private final Long groupId;

    public LeaveGroupResponse(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }
}
