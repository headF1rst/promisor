package promisor.promisor.domain.member.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException{
    public MemberNotFoundException(){super(ErrorCode.MEMBER_EMPTY);}
}
