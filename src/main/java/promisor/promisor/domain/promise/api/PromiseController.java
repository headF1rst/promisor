package promisor.promisor.domain.promise.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promisor.promisor.domain.promise.service.PromiseService;
import promisor.promisor.global.auth.JwtAuth;

@RestController
@RequestMapping("/promises")
@RequiredArgsConstructor
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping
    public ResponseEntity<Void> createPromise(@JwtAuth String email,
                                              @RequestParam("name") String name) {
        promiseService.createPromise(email, name);
        return ResponseEntity.ok().build();
    }
}
