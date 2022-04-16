package promisor.promisor.domain.team.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class TeamNotFoundForMember extends EntityNotFoundException {
    public TeamNotFoundForMember(ErrorCode errorCode) {
        super(errorCode);
    }

    public TeamNotFoundForMember(String message) {
        super(message);
    }

    public TeamNotFoundForMember() {
        super(ErrorCode.TEAM_NOT_FOUND_FOR_MEMBER);
    }
}
