package promisor.promisor.domain.calender.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class ReasonEmptyException extends EntityNotFoundException {
    public ReasonEmptyException() {super(ErrorCode.REASON_EMPTY);}
}

