package promisor.promisor.domain.BanDate.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.BanDate.dto.RegisterDateDto;
import promisor.promisor.domain.BanDate.dto.RegisterPersonalBanDateResponse;
import promisor.promisor.domain.BanDate.service.BanDateService;
import promisor.promisor.global.auth.JwtAuth;

@RestController
@RequestMapping("/bandate")
@RequiredArgsConstructor
public class BanDateController {
    private final BanDateService banDateService;

    @Operation(summary = "register personal ban date", description = "개인 일정 등록")
    @PostMapping("/personal")
    public ResponseEntity<RegisterPersonalBanDateResponse> registerPersonal(@JwtAuth String email,
                                                                            @RequestBody RegisterDateDto registerDateDto){
        RegisterPersonalBanDateResponse response = banDateService.registerPersonal(email, registerDateDto.getDate(),registerDateDto.getReason());
        return ResponseEntity.ok().body(response);
    }
}
