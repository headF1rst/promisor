package promisor.promisor.domain.promise.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.InvalidValueException;

public class MemberNotBelongsToTeam extends InvalidValueException {
    public MemberNotBelongsToTeam(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public MemberNotBelongsToTeam(String message) {
        super(message);
    }

    public MemberNotBelongsToTeam() {
        super(ErrorCode.MEMBER_NOT_BELONGS_TO_TEAM);
    }
}
