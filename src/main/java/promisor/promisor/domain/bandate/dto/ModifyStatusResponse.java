package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ModifyStatusResponse {
    Long memberId;
    LocalDate date;
    String dateStatus;

    public ModifyStatusResponse(Long id, LocalDate date, String dateStatus) {
        this.memberId=id;
        this.date=date;
        this.dateStatus=dateStatus;
    }
}
