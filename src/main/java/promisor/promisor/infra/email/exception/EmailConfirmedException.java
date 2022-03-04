package promisor.promisor.infra.email.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class EmailConfirmedException extends InvalidValueException {

    public EmailConfirmedException() {
        super(ErrorCode.EMAIL_CONFIRMED);
    }
}
