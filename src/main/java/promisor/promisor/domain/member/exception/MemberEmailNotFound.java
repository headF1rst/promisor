package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class MemberEmailNotFound extends EntityNotFoundException {
    public MemberEmailNotFound() {
        super(ErrorCode.EMAIL_MEMBER_NOT_FOUND);
    }
}
