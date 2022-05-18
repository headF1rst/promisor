package promisor.promisor.domain.BanDate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalBanDateResponse {
    private Long id;
    private Date date;
    private String reason;

    public RegisterPersonalBanDateResponse(Long id, Date date, String reason){
        this.id = id;
        this.date = date;
        this.reason = reason;
    }
}
