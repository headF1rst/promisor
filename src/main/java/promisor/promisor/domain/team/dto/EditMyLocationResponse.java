package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class EditMyLocationResponse {
    private final float latitude;
    private final float longitude;

    public EditMyLocationResponse(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
