package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class ForbiddenUserException extends InvalidValueException {

    public ForbiddenUserException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ForbiddenUserException(String message) {
        super(message, ErrorCode.FORBIDDEN_USER);
    }

    public ForbiddenUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
