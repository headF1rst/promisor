package promisor.promisor.domain.team.dto.response;

import lombok.Getter;

@Getter
public class GetTeamMembersLocationResponse {

    private final Long teamId;
    private final Long memberId;
    private final String name;
    private final double latitude;
    private final double longitude;

    public GetTeamMembersLocationResponse(Long teamId, Long memberId, String name, double latitude, double longitude) {
        this.teamId = teamId;
        this.memberId = memberId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
