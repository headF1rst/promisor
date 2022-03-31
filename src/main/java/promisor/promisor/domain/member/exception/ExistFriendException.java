package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class ExistFriendException extends InvalidValueException {

    public ExistFriendException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ExistFriendException(String message) {
        super(message, ErrorCode.EXIST_FRIEND);
    }

    public ExistFriendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
