package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class EmailDuplicatedException extends InvalidValueException {

    public EmailDuplicatedException(String email) {
        super(email, ErrorCode.EMAIL_ALREADY_TAKEN);
    }
}
