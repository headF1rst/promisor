package promisor.promisor.domain.calender.dto;

import lombok.Data;
import promisor.promisor.domain.calender.domain.DateStatusType;

import java.time.LocalDate;

@Data
public class ModifyStatusResponse {
    Long memberId;
    LocalDate date;
    DateStatusType dateStatus;

    public ModifyStatusResponse(Long id, LocalDate date, DateStatusType dateStatus) {
        this.memberId=id;
        this.date=date;
        this.dateStatus=dateStatus;
    }
}
