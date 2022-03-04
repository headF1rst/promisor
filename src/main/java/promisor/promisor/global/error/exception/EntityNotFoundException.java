package promisor.promisor.global.error.exception;

import promisor.promisor.global.error.ErrorCode;

public class EntityNotFoundException extends ApplicationException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
