package promisor.promisor.domain.team.dto.request;

import lombok.Data;

@Data
public class InviteTeamRequest {
    private Long[] memberId;
    private Long teamId;
}
