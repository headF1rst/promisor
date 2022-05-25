package promisor.promisor.domain.bandate.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import promisor.promisor.domain.bandate.dto.GetTeamCalendarResponse;
import promisor.promisor.domain.bandate.dto.PersonalBanDateStatusEditRequest;
import promisor.promisor.domain.bandate.dto.RegisterDateDto;
import promisor.promisor.domain.bandate.dto.RegisterPersonalBanDateResponse;
import promisor.promisor.domain.bandate.service.BanDateService;
import promisor.promisor.global.auth.JwtAuth;

import java.util.List;

@RestController
@RequestMapping("/bandate")
@RequiredArgsConstructor
public class BanDateController {
    private final BanDateService banDateService;

    @Operation(summary = "register personal ban date", description = "개인 일정 등록")
    @PostMapping("/personal")
    public ResponseEntity<RegisterPersonalBanDateResponse> registerPersonal(@JwtAuth String email,
                                                                            @RequestBody RegisterDateDto registerDateDto){
        RegisterPersonalBanDateResponse response = banDateService.registerPersonal(email, registerDateDto.getDate(),
                                                                                    registerDateDto.getReason());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "edit personal ban date", description = "개인 캘린더 상태 변경")
    @PatchMapping
    public ResponseEntity<Void> editPersonalBanDateStatus(@JwtAuth String email,
                                                          @RequestBody PersonalBanDateStatusEditRequest request) {
        banDateService.editPersonalBanDateStatus(email, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "get team calendar", description = "팀 캘린더 조회")
    @GetMapping("/team/{id}")
    public ResponseEntity<List<GetTeamCalendarResponse>> getTeamCalendar(@JwtAuth String email,
                                                                         @PathVariable("id") Long teamId) {
        List<GetTeamCalendarResponse> response = banDateService.getTeamCalendar(email, teamId);
        return ResponseEntity.ok().body(response);
    }
}
