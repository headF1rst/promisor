package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class NoRightToDelegateException extends InvalidValueException {
    public NoRightToDelegateException(){
        super(ErrorCode.NO_RIGHT_TO_DELEGATE);
    }
}
