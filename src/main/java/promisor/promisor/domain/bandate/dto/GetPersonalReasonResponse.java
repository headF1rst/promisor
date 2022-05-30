package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetPersonalReasonResponse {
    private Long id;
    private Long personalBandateId;
    private LocalDate date;
    private String reason;

    public GetPersonalReasonResponse(Long id, Long personalBandateId, LocalDate date, String reason) {
        this.id = id;
        this.personalBandateId = personalBandateId;
        this.date = date;
        this.reason = reason;
    }
}
