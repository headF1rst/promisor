package promisor.promisor.global.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {

    private int code = HttpStatus.OK.value();
    private Object result;

    public ApiResponse(int code, Object result) {
        this.code = code;
        this.result = result;
    }

    public void changeResult(Object result) {
        this.result = result;
    }
}
