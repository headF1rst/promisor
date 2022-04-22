package promisor.promisor.domain.promise.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class PromiseIdNotFound extends EntityNotFoundException {
    public PromiseIdNotFound() {super(ErrorCode.PROMISE_ID_NOT_FOUND);}
}
