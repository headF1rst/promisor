package promisor.promisor.domain.member.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;
    private final Long refreshTokenId;

    public LoginResponse(String accessToken, Long refreshTokenId) {
        this.accessToken = accessToken;
        this.refreshTokenId = refreshTokenId;
    }
}
