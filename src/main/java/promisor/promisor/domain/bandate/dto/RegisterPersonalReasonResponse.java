package promisor.promisor.domain.bandate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalReasonResponse {
    private Long id;
    private Long pbd_id;
    private String reason;

    public RegisterPersonalReasonResponse(Long id, Long pbd_id, String reason){
        this.id = id;
        this.pbd_id = pbd_id;
        this.reason = reason;
    }
}
