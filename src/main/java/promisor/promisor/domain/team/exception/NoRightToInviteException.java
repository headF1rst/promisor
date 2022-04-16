package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class NoRightToInviteException extends InvalidValueException {
    public NoRightToInviteException() {
        super(ErrorCode.NO_RIGHT_TO_INVITE);
    }
}
