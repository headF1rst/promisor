package promisor.promisor.global.secret;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * KEY를 가져다 줄 클래스 SecretKey
 *
 * @author Sanha Ko
 */
@Component("SecretKey")
@Getter
public class SecretKey {

    @Value("${spring.security.kakao.apikey}")
    private String kakaoApiKey;

    @Value("${spring.security.jwt.token.secret-key}")
    private String jwtSecretKey;

    @Value("${spring.security.jwt.token.expire-length}")
    private Integer jwtValidityTime;

    private SecretKey() {}


}
