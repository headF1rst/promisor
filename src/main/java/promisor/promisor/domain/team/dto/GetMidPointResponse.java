package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class GetMidPointResponse {

    private final Long teamId;
    private final float latitude;
    private final float longitude;

    public GetMidPointResponse(Long teamId, float latitude, float longitude) {
        this.teamId = teamId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
