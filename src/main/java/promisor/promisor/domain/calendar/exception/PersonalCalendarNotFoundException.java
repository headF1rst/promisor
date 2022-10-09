package promisor.promisor.domain.calendar.exception;

import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.EntityNotFoundException;

public class PersonalCalendarNotFoundException extends EntityNotFoundException{
    public PersonalCalendarNotFoundException() {super(ErrorCode.BANDATE_NOT_FOUND);}
}
