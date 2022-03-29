package promisor.promisor.global.token.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class InvalidTokenException extends InvalidValueException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
