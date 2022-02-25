package promisor.promisor.domain.user.dto;

import lombok.Data;
import promisor.promisor.domain.user.User;
import promisor.promisor.domain.user.UserRole;

@Data
public class SignUpDto {

    private final String name;
    private final String email;
    private final String password;
    private final String telephone;
    private final UserRole userRole;
}
