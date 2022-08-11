package promisor.promisor.domain.calender.dto;

import lombok.Data;
import promisor.promisor.domain.calender.domain.DateStatusType;

import java.util.List;

@Data
public class GetPersonalScheduleResponse {
    private DateStatusType dateStatus;
    private List<String> reason;

    public GetPersonalScheduleResponse(DateStatusType dateStatus, List<String> reason) {
        this.dateStatus = dateStatus;
        this.reason = reason;
    }

    public GetPersonalScheduleResponse(String dateStatus) {
        this.dateStatus = DateStatusType.valueOf(dateStatus);
    }
}
