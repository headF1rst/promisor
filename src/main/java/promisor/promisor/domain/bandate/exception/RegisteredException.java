package promisor.promisor.domain.bandate.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class RegisteredException extends EntityNotFoundException {
    public RegisteredException(){super(ErrorCode.INVALID_INPUT_VALUE);}
}
