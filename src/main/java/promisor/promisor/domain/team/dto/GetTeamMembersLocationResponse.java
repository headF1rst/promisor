package promisor.promisor.domain.team.dto;

import lombok.Getter;

@Getter
public class GetTeamMembersLocationResponse {

    private final Long teamId;
    private final Long memberId;
    private final String name;
    private final float latitude;
    private final float longitude;

    public GetTeamMembersLocationResponse(Long teamId, Long memberId, String name, float latitude, float longitude) {
        this.teamId = teamId;
        this.memberId = memberId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
