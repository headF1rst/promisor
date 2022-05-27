package promisor.promisor.domain.team.dto;

import lombok.Data;

@Data
public class EditMyLocationDto {
    private Long teamId;
    private float latitude;
    private float longitude;
}
