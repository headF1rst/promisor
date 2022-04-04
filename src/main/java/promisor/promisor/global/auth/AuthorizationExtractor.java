package promisor.promisor.global.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    public static final String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {

        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        log.info("Header Message: '{}'", headers);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE,
                        value.substring(0, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
