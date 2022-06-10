package promisor.promisor.domain.team.dto.request;

import lombok.Data;

@Data
public class EditMyLocationRequest {
    private Long teamId;
    private double latitude;
    private double longitude;
}
