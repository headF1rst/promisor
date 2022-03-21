package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class MemberEmailNotFound extends InvalidValueException {
    public MemberEmailNotFound() {
        super(ErrorCode.EMAIL_MEMBER_NOT_FOUND);
    }
}
