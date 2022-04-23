package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class DelegateLeaderResponse {
    private final Long memberId;
    private final Long groupId;

    public DelegateLeaderResponse(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }
}
