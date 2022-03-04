package promisor.promisor.infra.email.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class EmailNotValid extends InvalidValueException {

    public EmailNotValid(String email) {
        super(email, ErrorCode.EMAIL_NOT_VALID);
    }
}
