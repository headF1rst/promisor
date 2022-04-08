package promisor.promisor.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import promisor.promisor.global.error.ErrorCode;

import java.util.function.Supplier;

@Getter
public class ApplicationException extends RuntimeException {

    private ErrorCode errorCode;

    protected ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
