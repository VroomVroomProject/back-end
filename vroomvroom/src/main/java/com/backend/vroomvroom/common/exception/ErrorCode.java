package com.backend.vroomvroom.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //400번 에러
    DUPLICATED_ENTITY(HttpStatus.BAD_REQUEST, "이미 존재하는 엔티티인 경우"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청한 값이 올바르지 않은 경우"),

    //401번 에러
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증에 실패한 경우"),

    //403번 에러
    ROLE_NOT_EXISTS(HttpStatus.FORBIDDEN, "권한이 없는 경우"),

    //404번 에러
    NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "존재하지 않는 엔티티인 경우"),

    //500번 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생한 경우")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
