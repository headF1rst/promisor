package promisor.promisor.domain.bandate.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class WrongAccess extends EntityNotFoundException {
    public WrongAccess(){super(ErrorCode.ACCESS_DENIED);}
}
