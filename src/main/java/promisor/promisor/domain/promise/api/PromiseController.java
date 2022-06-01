package promisor.promisor.domain.promise.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.promise.dto.PromiseCreateRequest;
import promisor.promisor.domain.promise.dto.PromiseDateEditRequest;
import promisor.promisor.domain.promise.dto.PromiseResponse;
import promisor.promisor.domain.promise.service.PromiseService;
import promisor.promisor.global.auth.JwtAuth;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/promises")
@RequiredArgsConstructor
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> createPromise(@JwtAuth final String email,
                                              @Valid @RequestBody final PromiseCreateRequest request,
                                              @PathVariable("id") final Long teamId) {
        promiseService.createPromise(email, request, teamId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> editPromise(@JwtAuth final String email,
                                            @RequestBody final PromiseDateEditRequest request,
                                            @PathVariable("id") final Long promiseId) {
        promiseService.editPromise(email, request, promiseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PromiseResponse>> searchPromiseList(@JwtAuth final String email,
                                                                   @PathVariable("id") final Long teamId) {
        List<PromiseResponse> response = promiseService.searchPromise(email, teamId);
        return ResponseEntity.ok().body(response);
    }
}
