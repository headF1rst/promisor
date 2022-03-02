package promisor.promisor.domain.member.dto;

import lombok.Data;
import promisor.promisor.domain.member.MemberRole;

@Data
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private String telephone;
    private MemberRole memberRole;
}
