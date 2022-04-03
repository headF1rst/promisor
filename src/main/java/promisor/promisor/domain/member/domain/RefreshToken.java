package promisor.promisor.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.BaseEntity;

import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    private String email;
    private String accessToken;
    private String refreshToken;
    private String refreshTokenExpirationAt;

    public RefreshToken(String email, String accessToken, String refreshToken, String refreshTokenExpirationAt) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationAt = refreshTokenExpirationAt;
    }

    public RefreshToken(String status, String email, String accessToken, String refreshToken, String refreshTokenExpirationAt) {
        super(status);
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationAt = refreshTokenExpirationAt;
    }
}
