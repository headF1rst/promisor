package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class LoginAgainException extends InvalidValueException {
    public LoginAgainException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public LoginAgainException(String message) {
        super(message);
    }

    public LoginAgainException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LoginAgainException(){
        super(ErrorCode.LOGIN_AGAIN);
    }
}
