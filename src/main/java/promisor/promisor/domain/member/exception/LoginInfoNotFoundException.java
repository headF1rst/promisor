package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class LoginInfoNotFoundException extends InvalidValueException {

    public LoginInfoNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public LoginInfoNotFoundException(String message) {
        super(message);
    }

    public LoginInfoNotFoundException() {
        super(ErrorCode.INVALID_LOGIN_INFO);
    }
}
