package promisor.promisor.domain.calender.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class PersonalBanDateNotFoundException extends EntityNotFoundException{
    public PersonalBanDateNotFoundException() {super(ErrorCode.BANDATE_NOT_FOUND);}
}
