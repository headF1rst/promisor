package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class MembernameNotFoundException extends InvalidValueException {
    public MembernameNotFoundException(String s) {
        super(ErrorCode.USER_NAME_NOT_FOUND);
    }
}
