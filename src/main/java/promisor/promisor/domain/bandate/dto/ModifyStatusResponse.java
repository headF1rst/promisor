package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ModifyStatusResponse {
    Long memberId;
    Date date;
    String dateStatus;

    public ModifyStatusResponse(Long id, Date date, String dateStatus) {
        this.memberId=id;
        this.date=date;
        this.dateStatus=dateStatus;
    }
}
