package promisor.promisor.domain.bandate.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import promisor.promisor.domain.bandate.dto.*;
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
                registerDateDto.getStatus());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "edit personal ban date", description = "개인 캘린더 상태 변경")
    @PatchMapping
    public ResponseEntity<ModifyStatusResponse> editPersonalBanDateStatus(@JwtAuth String email,
                                                          @RequestBody ModifyStatusDto request) {
        ModifyStatusResponse response = banDateService.editPersonalBanDateStatus(email, request.getDate(), request.getStatus());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "get team calendar", description = "팀 캘린더 조회")
    @GetMapping("/team/{id}")
    public ResponseEntity<List<GetTeamCalendarResponse>> getTeamCalendar(@JwtAuth String email,
                                                                         @PathVariable("id") Long teamId) {
        List<GetTeamCalendarResponse> response = banDateService.getTeamCalendar(email, teamId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "register personal reason", description = "사적인 이유 등록")
    @PostMapping("/personal/reason")
    public ResponseEntity<RegisterPersonalReasonResponse> registerPersonalReason(@JwtAuth String email,
                                                       @RequestBody RegisterReasonDto registerReasonDto){
        RegisterPersonalReasonResponse response = banDateService.registerPersonalReason(email, registerReasonDto.getDate(),
                registerReasonDto.getReason());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "get personal calendar status", description = "개인 캘린더 일정 조회")
    @GetMapping("/personal")
    public ResponseEntity<List<GetPersonalCalendarResponse>> getPersonalCalendar(@JwtAuth String email) {
        List<GetPersonalCalendarResponse> response = banDateService.getPersonalCalendar(email);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "get personal calendar reason", description = "개인 캘린더 일정에 대한 사유 조회")
    @GetMapping("/personal/reason")
    public ResponseEntity<List<GetPersonalReasonResponse>> getPersonalReason(@JwtAuth String email) {
        List<GetPersonalReasonResponse> response = banDateService.getPersonalReason(email);
        return ResponseEntity.ok().body(response);
    }
}
