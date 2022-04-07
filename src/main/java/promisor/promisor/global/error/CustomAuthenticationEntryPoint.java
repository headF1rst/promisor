package promisor.promisor.global.error;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;

        /*
            * 토큰이 만료된 경우 예외처리
         */
        if (exception == null) {
            errorCode = ErrorCode.UNAUTHORIZED_USER;
            setResponse(response, errorCode);
            return;
        }

        if (exception.equals("ExpiredJwtException")) {
            errorCode = ErrorCode.TOKEN_EXPIRED;
            setResponse(response, errorCode);
            return;
        }

        if (exception.equals("JwtException")){
            errorCode = ErrorCode.INVALID_TOKEN;
            setResponse(response, errorCode);
            return;
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        response.getWriter().print(json);
    }
}
