package promisor.promisor.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {
    // -----예외(Http 상태, 에러코드, 메시지)------ //

    // Date
    DATE_EMPTY(400,"D001" ,"날짜를 입력하지 않았습니다."),

    // Calendar
    BANDATE_NOT_FOUND(400, "B001", "일정이 등록되지 않았습니다."),

    // Common 공통 예외
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력입니다."),
    ACCESS_DENIED(400, "C002", "접근이 제한되었습니다."),
    RESOURCE_NOT_FOUND(204, "C003", "자원을 찾지 못했습니다."),
    ENTITY_NOT_FOUND(400, "C004", "엔티티를 찾지 못했습니다."),
    NAME_EMPTY(400, "C005", "이름이 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "C006", "서버에 문제가 발생했습니다."),
    FORBIDDEN_USER(403, "C007", "해당 요청에 대한 권한이 없습니다."),
    TooManyRequestException(429, "C008", "너무 많은 요청입니다. 30초 후에 다시 시도해주세요."),

    // Group
    GROUP_ID_NOT_FOUND( 400, "G001", "해당 그룹이 존재하지 않습니다."),
    NO_RIGHTS(401, "G002", "해당 그룹에 대한 수정 권한이 없습니다."),
    TEAM_NOT_FOUND_FOR_MEMBER(400, "G003", "해당 멤버가 속한 그룹을 찾을 수 없습니다."),  
    GROUP_EMPTY(400,"G004","해당 그룹이 없습니다."),

    // Unique
    NO_RIGHT_TO_INVITE(401,"U001","초대 권한이 없습니다."),
    NO_RIGHT_TO_DELEGATE(401,"U002","위임 권한이 없습니다."),

    // Promise
    PROMISE_ID_NOT_FOUND(400, "P001", "해당 약속이 없습니다."),

    // Member
    EMAIL_ALREADY_TAKEN(400, "M001", "이미 존재하는 이메일입니다."),
    EMAIL_SENDING_FAIL(400, "M002", "이메일을 전송하는데 실패하였습니다."),
    EMAIL_CONFIRMED(400, "M003", "이미 인증된 이메일입니다."),
    EMAIL_NOT_VALID(400,"M004" ,"유효하지 않은 이메일입니다. 다시 입력해주세요."),
    TOKEN_EXPIRED(401, "M005", "토큰이 만료되었습니다."),
    INVALID_TOKEN(400, "M006","잘못된 토큰입니다."),
    USER_NAME_NOT_FOUND(400, "M007", "해당 이름의 사용자를 찾을수 없습니다."),
    EMAIL_MEMBER_NOT_FOUND(400, "M008", "해당 이메일의 사용자가 존재하지 않습니다."),
    TOKEN_NOT_EXIST(400, "M009", "토큰을 입력해 주세요."),
    EMAIL_EMPTY(400, "M010", "이메일을 입력해주세요."),
    PASSWORD_EMPTY(400, "M011", "비밀번호를 입력해주세요."),
    INVALID_LOGIN_INFO(400, "M012", "이메일 또는 비밀번호를 다시 확인해 주세요."),
    EXIST_FRIEND(400,"M013", "이미 등록된 친구입니다."),
    UNAUTHORIZED_USER(401, "M014", "로그인 후 이용가능합니다."),
    MEMBER_EMPTY(400,"M015","해당 멤버가 없습니다."),
    LOGIN_AGAIN(400, "M016", "로그인을 다시 시도해 주세요"),
    MEMBER_NOT_BELONGS_TO_TEAM(400, "M017", "해당 그룹에 속하지 않는 회원입니다."),

    // Reason
    REASON_EMPTY(400,"R001" ,"무슨 일정인지 입력해주세요"),

    // Status
    STATUS_EMPTY(400,"S001" ,"상태를 입력해주세요." );


    private int httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final int httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
