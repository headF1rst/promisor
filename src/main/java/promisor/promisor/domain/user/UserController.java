package promisor.promisor.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import promisor.promisor.domain.user.dto.SignUpDto;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public String register(@RequestBody SignUpDto request) {
        return userService.register(request);
    }
}
