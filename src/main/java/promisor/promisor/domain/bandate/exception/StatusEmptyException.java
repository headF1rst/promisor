package promisor.promisor.domain.bandate.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class StatusEmptyException extends EntityNotFoundException {
    public StatusEmptyException(){
        super(ErrorCode.STATUS_EMPTY);
    }
}
