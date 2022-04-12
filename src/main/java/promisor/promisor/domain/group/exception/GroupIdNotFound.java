package promisor.promisor.domain.group.exception;


import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class GroupIdNotFound extends EntityNotFoundException {
    public GroupIdNotFound() {super(ErrorCode.GROUP_ID_NOT_FOUND);}
}
