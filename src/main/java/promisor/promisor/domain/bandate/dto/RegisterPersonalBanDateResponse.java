package promisor.promisor.domain.bandate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalBanDateResponse {
    private Long id;
    private Date date;

    public RegisterPersonalBanDateResponse(Long id, Date date){
        this.id = id;
        this.date = date;
    }
}
