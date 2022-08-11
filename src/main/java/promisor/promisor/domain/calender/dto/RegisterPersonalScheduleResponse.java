package promisor.promisor.domain.calender.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalScheduleResponse {
    private Long id;
    private Long pbd_id;
    private String reason;

    public RegisterPersonalScheduleResponse(Long id, Long pbd_id, String reason){
        this.id = id;
        this.pbd_id = pbd_id;
        this.reason = reason;
    }
}
