package com.backend.vroomvroom.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    public CommonException(ErrorCode errorCode, String message)  {
        super(message);
        this.errorCode = errorCode;
    }



}
