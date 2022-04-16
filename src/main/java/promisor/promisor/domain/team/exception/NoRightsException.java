package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class NoRightsException extends InvalidValueException {

    public NoRightsException(){
        super(ErrorCode.NO_RIGHTS);
    }

    public NoRightsException(String message) { super(message);}
}
