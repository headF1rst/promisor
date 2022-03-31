package promisor.promisor.global.token.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class TokenNotExistException extends EntityNotFoundException {

    public TokenNotExistException() {
        super(ErrorCode.TOKEN_NOT_EXIST);
    }
}
