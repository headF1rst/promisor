package promisor.promisor.domain.user.dto;

import lombok.Data;
import promisor.promisor.domain.user.User;
import promisor.promisor.domain.user.UserRole;

@Data
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private String telephone;
    private UserRole userRole;
}
