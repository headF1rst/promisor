package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class GetMidPointResponse {

    private final Long teamId;
    private final double latitude;
    private final double longitude;

    public GetMidPointResponse(Long teamId, double latitude, double longitude) {
        this.teamId = teamId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
