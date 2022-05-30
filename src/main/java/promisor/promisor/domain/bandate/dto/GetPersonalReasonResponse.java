package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetPersonalReasonResponse {
    private Long id;
    private Long personalBandateId;
    private Date date;
    private String reason;

    public GetPersonalReasonResponse(Long id, Long personalBandateId, Date date, String reason) {
        this.id = id;
        this.personalBandateId = personalBandateId;
        this.date = date;
        this.reason = reason;
    }
}
