package promisor.promisor.domain.team.dto.response;

import lombok.Getter;

@Getter
public class EditMyLocationResponse {
    private final double latitude;
    private final double longitude;

    public EditMyLocationResponse(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
