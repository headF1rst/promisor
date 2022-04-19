package promisor.promisor.domain.promise.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.promise.dto.PromiseCreateRequest;
import promisor.promisor.domain.promise.service.PromiseService;
import promisor.promisor.global.auth.JwtAuth;

@RestController
@RequestMapping("/promises")
@RequiredArgsConstructor
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping
    public ResponseEntity<Void> createPromise(@JwtAuth String email,
                                              @RequestBody PromiseCreateRequest request,
                                              @RequestParam("teamId") Long teamId) {
        promiseService.createPromise(email, request, teamId);
        return ResponseEntity.ok().build();
    }
}
