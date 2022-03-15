package promisor.promisor.domain.member.exception;

import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

import javax.validation.constraints.NotEmpty;

public class NameEmptyException extends InvalidValueException {
    public NameEmptyException() {
        super(ErrorCode.NAME_EMPTY);
    }
}
