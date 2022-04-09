package promisor.promisor.global.token;

import lombok.Data;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

@Data
public class JwtExceptionResponse {

    private final String message;
    private final HttpStatus unauthorized;


    public int convertToJson() {
        JSONObject json = new JSONObject();

        json.put("code", unauthorized);
        json.put("message", message);
        return 1;
    }
}
