package nnz.userservice.exception;

import io.github.eello.nnz.common.exception.AbstractErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
public enum ErrorCode implements AbstractErrorCode {

    DUPLICATE_EMAIL("U001", "이미 등록된 이메일입니다.", CONFLICT),
    DUPLICATE_NICKNAME("U002", "이미 등록된 닉네임입니다.", CONFLICT),
    INVALID_NICKNAME_PATTERN("U003", "올바르지 않은 닉네임 형식입니다.", BAD_REQUEST),
    INVALID_PWD_PATTERN("U004", "올바르지 않은 비밀번호 형식입니다.", BAD_REQUEST),
    INVALID_EMAIL_PATTERN("U005", "올바르지 않은 이메일 형식입니다.", BAD_REQUEST),
    NOT_FOUND_VERIFY("U006", "존재하지 않는 인증정보입니다.", NOT_FOUND),
    NOT_VERIFIED("U007", "본인확인이 되지 않았습니다.", BAD_REQUEST),
    PWD_NOT_MATCH_CONFIRM_PWD("U008", "비밀번호와 비밀번호 확인이 일치하지 않습니다.", BAD_REQUEST),
    USER_NOT_FOUND("U009", "등록되지 않은 사용자입니다.", NOT_FOUND),
    LOGIN_FAILURE("U010", "이메일 또는 비밀번호를 확인해주세요.", OK),
    ALREADY_FOLLOWING("U011", "이미 팔로우 중입니다.", CONFLICT),
    NOT_FOLLOWING("U012", "팔로우 중이 아닙니다.", BAD_REQUEST),
    OLD_PASSWORD_IS_REQUIRED("U013", "기존 패스워드는 필수입니다.", BAD_REQUEST),
    PROFILE_IMAGE_UPLOAD_FAIL("U014", "프로필 이미지 업로드에 실패하였습니다.", INTERNAL_SERVER_ERROR),
    DUPLICATE_PHONE_NUMBER("U015", "해당 번호로 가입된 계정이 존재합니다.", CONFLICT),

    REFRESH_TOKEN_NOT_FOUND("RT001", "존재하지 않는 리프레시 토큰입니다.", NOT_FOUND),
    REFRESH_TOKEN_NOT_MATCHED("RT002", "리프레시 토큰이 일치하지 않습니다.", BAD_REQUEST),
    REFRESH_TOKEN_NOT_VALID("RT003", "만료되거나 유효하지 않는 리프레시 토큰입니다.", BAD_REQUEST),
    REFRESH_TOKEN_NOT_IN_COOKIE("RT004", "쿠키에 리프레시 토큰이 존재하지 않습니다.", BAD_REQUEST),


    NANUM_NOT_FOUND("N001", "존재하지 않는 나눔입니다.", BAD_REQUEST),

    NOT_PROVIDER("N002", "해당 나눔의 나눔자가 아닙니다.", FORBIDDEN),


    ALREADY_BOOKMARKED("BM001", "이미 찜한 나눔입니다.", CONFLICT),
    NOT_BOOKMARK("BM002", "찜한 나눔이 아닙니다.", BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
