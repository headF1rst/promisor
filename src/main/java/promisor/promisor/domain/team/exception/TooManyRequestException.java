package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class TooManyRequestException extends InvalidValueException {


    public TooManyRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TooManyRequestException(String message) {
        super(message);
    }

    public TooManyRequestException() {
        super(ErrorCode.TooManyRequestException);
    }
}
