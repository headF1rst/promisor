package promisor.promisor.domain.member.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;
    private final Long refreshTokenId;
    private final Long tokenExpireTime;

    public LoginResponse(String accessToken, Long refreshTokenId, Long tokenExpireTime) {
        this.accessToken = accessToken;
        this.refreshTokenId = refreshTokenId;
        this.tokenExpireTime = tokenExpireTime;
    }
}
