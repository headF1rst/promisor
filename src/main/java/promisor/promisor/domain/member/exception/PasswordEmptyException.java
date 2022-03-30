package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class PasswordEmptyException extends EntityNotFoundException {

    public PasswordEmptyException() {
        super(ErrorCode.PASSWORD_EMPTY);
    }

    public PasswordEmptyException(String message) {
        super(message);
    }
}
