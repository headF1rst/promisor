package promisor.promisor.global.error.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.ApplicationException;

public class InvalidValueException extends ApplicationException {

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
