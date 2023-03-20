package com.backend.vroomvroom.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.backend.vroomvroom.common.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        log.error("handleCommonException throw CommonException : {}", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode(), e.getMessage());
    }

    /**
     * @Valid 유효성 검증이 실패할 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException throw MethodArgumentNotValidException : {}", e);
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(". ");
        }
        return ErrorResponse.toResponseEntity(INVALID_REQUEST, builder.toString());
    }


    /**
     * 예상하지 못한 예외가 발생할 경우
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException throw Exception : {}", e);
        return ErrorResponse.toResponseEntity(INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
    }
}
