package promisor.promisor.domain.bandate.dto;

import lombok.Data;
import promisor.promisor.domain.bandate.domain.DateStatusType;

import java.util.List;

@Data
public class GetPersonalReasonResponse {
    private DateStatusType dateStatus;
    private List<String> reason;

    public GetPersonalReasonResponse(DateStatusType dateStatus, List<String> reason) {
        this.dateStatus = dateStatus;
        this.reason = reason;
    }

    public GetPersonalReasonResponse(String dateStatus) {
        this.dateStatus = DateStatusType.valueOf(dateStatus);
    }
}
