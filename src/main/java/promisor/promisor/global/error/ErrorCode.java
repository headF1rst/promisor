package promisor.promisor.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {
    // -----예외(Http 상태, 에러코드, 메시지)------ //

    // Common 공통 예외
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력입니다."),
    ACCESS_DENIED(400, "C002", "접근이 제한되었습니다."),
    RESOURCE_NOT_FOUND(204, "C003", "자원을 찾지 못했습니다."),
    ENTITY_NOT_FOUND(400, "C004", "엔티티를 찾지 못했습니다."),
    NAME_EMPTY(400, "C005", "이름이 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "C006", "서버에 문제가 발생했습니다."),

    // Member
    EMAIL_ALREADY_TAKEN(400, "M001", "이미 존재하는 이메일입니다."),
    EMAIL_SENDING_FAIL(400, "M002", "이메일을 전송하는데 실패하였습니다."),
    EMAIL_CONFIRMED(400, "M003", "이미 인증된 이메일입니다."),
    EMAIL_NOT_VALID(400,"M004" ,"유효하지 않은 이메일입니다. 다시 입력해주세요."),
    TOKEN_EXPIRED(400, "M005", "토큰이 만료되었습니다."),
    INVALID_TOKEN(400, "M006","잘못된 토큰입니다."),
    USER_NAME_NOT_FOUND(400, "M007", "해당 이름의 사용자를 찾을수 없습니다."),
    EMAIL_MEMBER_NOT_FOUND(400, "M008", "해당 이메일의 사용자가 존재하지 않습니다."),
    Token_Not_Exist(400, "M009", "토큰을 입력해 주세요.");

    // Group

    private int httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final int httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
