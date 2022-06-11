package promisor.promisor.domain.team.dto.request;

import lombok.Data;

@Data
public class DelegateLeaderRequest {
    private Long groupId;
    private Long memberId;
}
