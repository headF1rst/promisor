package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class LeaderLeaveException extends InvalidValueException {
    public LeaderLeaveException() {super(ErrorCode.ACCESS_DENIED);}
}

