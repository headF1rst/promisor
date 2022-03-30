package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class EmailEmptyException extends EntityNotFoundException {

    public EmailEmptyException() {
        super(ErrorCode.EMAIL_EMPTY);
    }

    public EmailEmptyException(String message) {
        super(message);
    }
}
