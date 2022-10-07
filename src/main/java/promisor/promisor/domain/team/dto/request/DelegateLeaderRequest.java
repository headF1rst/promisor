package promisor.promisor.domain.team.dto.request;

import lombok.Data;

@Data
public class DelegateLeaderRequest {
    private Long teamId;
    private Long memberId;
}
