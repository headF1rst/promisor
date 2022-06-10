package promisor.promisor.domain.promise.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.promise.dto.request.PromiseCreateRequest;
import promisor.promisor.domain.promise.dto.request.PromiseDateEditRequest;
import promisor.promisor.domain.promise.dto.response.PromiseResponse;
import promisor.promisor.domain.promise.service.PromiseService;
import promisor.promisor.global.auth.JwtAuth;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/promises")
@RequiredArgsConstructor
public class PromiseController {

    private final PromiseService promiseService;

    @Operation(summary = "Create Promise", description = "약속 생성")
    @PostMapping("/{id}")
    public ResponseEntity<Void> createPromise(@JwtAuth final String email,
                                              @Valid @RequestBody final PromiseCreateRequest request,
                                              @PathVariable("id") final Long teamId) {
        promiseService.createPromise(email, request, teamId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update Promise", description = "약속 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> editPromise(@JwtAuth final String email,
                                            @RequestBody final PromiseDateEditRequest request,
                                            @PathVariable("id") final Long promiseId) {
        promiseService.editPromise(email, request, promiseId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Find Promise List", description = "약속 전체 조회")
    @GetMapping("/list/{id}")
    public ResponseEntity<List<PromiseResponse>> searchPromiseList(@JwtAuth final String email,
                                                                   @PathVariable("id") final Long teamId) {
        List<PromiseResponse> response = promiseService.searchPromises(email, teamId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Find Promise", description = "약속 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PromiseResponse> searchPromise(@JwtAuth final String email,
                                                         @PathVariable("id") final Long promiseId) {
        PromiseResponse response = promiseService.searchPromise(email, promiseId);
        return ResponseEntity.ok().body(response);
    }
}
