package promisor.promisor.global.auth.secret;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretConfig {

    private final String jwtSecretKey;
    private final Long jwtValidityTime;
    private final Long refreshValidityTime;

    @Bean
    public SecretKey newSecretKey() {
        return new SecretKey(jwtSecretKey, jwtValidityTime, refreshValidityTime);
    }

    public SecretConfig(@Value("${security.jwt.token.secret-key}") String jwtSecretKey,
                        @Value("${security.jwt.token.expire-length}")Long jwtValidityTime,
                        @Value("${security.jwt.token.expire-length-refresh}") Long refreshValidityTime) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtValidityTime = jwtValidityTime;
        this.refreshValidityTime = refreshValidityTime;
    }
}
