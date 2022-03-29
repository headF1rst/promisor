package promisor.promisor.global.token.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class TokenNotExistException extends EntityNotFoundException {

    public TokenNotExistException() {
        super(ErrorCode.Token_Not_Exist);
    }
}
