package promisor.promisor.global.token;

import lombok.Data;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import promisor.promisor.global.error.ErrorResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Data
public class JwtExceptionResponse {

    private final int httpStatus;
    private final String code;
    private final String message;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        json.put("httpStatus", this.httpStatus);
        json.put("code", this.code);
        json.put("message", this.message);

        return json;
    }
}
