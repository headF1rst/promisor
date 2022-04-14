package promisor.promisor.domain.team.exception;


import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class TeamIdNotFound extends EntityNotFoundException {
    public TeamIdNotFound() {super(ErrorCode.GROUP_ID_NOT_FOUND);}
}
