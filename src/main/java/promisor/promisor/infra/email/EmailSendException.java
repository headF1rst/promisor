package promisor.promisor.infra.email;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class EmailSendException extends InvalidValueException {

    public EmailSendException(String email) {
        super(email, ErrorCode.EMAIL_SENDING_FAIL);
    }
}
