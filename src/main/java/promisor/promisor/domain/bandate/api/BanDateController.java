package promisor.promisor.domain.bandate.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import promisor.promisor.domain.bandate.dto.*;
import promisor.promisor.domain.bandate.exception.DateEmptyException;
import promisor.promisor.domain.bandate.service.BanDateService;
import promisor.promisor.global.auth.JwtAuth;

import javax.validation.Valid;
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
                                                                          @RequestBody @Valid ModifyStatusDto request) {
        ModifyStatusResponse response = banDateService.editPersonalBanDateStatus(email, request.getDate(), request.getStatus());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "get team calendar detail", description = "팀 캘린더 날짜 별 세부 조회")
    @GetMapping("/team/{id}/detail/{date}")
    public ResponseEntity<List<GetTeamCalendarResponse>> getTeamCalendar(@JwtAuth String email,
                                                                         @PathVariable("id") Long teamId,
                                                                         @PathVariable("date") String date) {
        List<GetTeamCalendarResponse> response = banDateService.getTeamCalendarDetail(email, teamId, date);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "register personal reason", description = "사적인 이유 등록")
    @PostMapping("/personal/reason")
    public ResponseEntity<RegisterPersonalReasonResponse> registerPersonalReason(@JwtAuth String email,
                                                       @RequestBody @Valid RegisterReasonDto registerReasonDto){
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
    @GetMapping("/personal/reason/{date}")
    public ResponseEntity<GetPersonalReasonResponse> getPersonalReason(@JwtAuth String email,
                                                                             @PathVariable("date") String date) {
        if (date == null){throw new DateEmptyException();}
        GetPersonalReasonResponse response = banDateService.getPersonalReason(email, date);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "get team calendar status", description = "팀 캘린더의 전체 상태 조회")
    @GetMapping("/team/{id}/{yearMonth}")
    public ResponseEntity<List<GetTeamCalendarStatusResponse>> getTeamCalendarStatus(@JwtAuth String email,
                                                                               @PathVariable("id") Long id,
                                                                               @PathVariable("yearMonth") String yearMonth) {
        List<GetTeamCalendarStatusResponse> response = banDateService.getTeamCalendarStatus(email, id, yearMonth);
        return ResponseEntity.ok().body(response);
    }
}
