package promisor.promisor.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.user.dto.SignUpDto;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public String register(@RequestBody SignUpDto request) {
        return userService.register(request);
    }

    @GetMapping("/users/confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }
}
