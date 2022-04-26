package promisor.promisor.domain.promise.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.promise.dto.PromiseCreateRequest;
import promisor.promisor.domain.promise.dto.PromiseDateEditRequest;
import promisor.promisor.domain.promise.dto.PromiseResponse;
import promisor.promisor.domain.promise.service.PromiseService;
import promisor.promisor.global.auth.JwtAuth;

import java.util.List;

@RestController
@RequestMapping("/promises")
@RequiredArgsConstructor
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> createPromise(@JwtAuth String email,
                                              @RequestBody PromiseCreateRequest request,
                                              @PathVariable("id") Long teamId) {
        promiseService.createPromise(email, request, teamId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> editPromiseDate(@JwtAuth String email,
                                                @RequestBody PromiseDateEditRequest request) {
        promiseService.editPromiseDate(email, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PromiseResponse>> searchPromiseList(@JwtAuth String email,
                                                                   @PathVariable("id") Long teamId) {
        List<PromiseResponse> response = promiseService.searchPromise(email, teamId);
        return ResponseEntity.ok().body(response);
    }
}
