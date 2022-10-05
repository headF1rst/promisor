package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class TeamNotFoundException extends EntityNotFoundException {
    public TeamNotFoundException() {
        super(ErrorCode.GROUP_EMPTY);
    }
}
