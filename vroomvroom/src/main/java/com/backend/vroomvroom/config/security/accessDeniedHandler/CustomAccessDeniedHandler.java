package com.backend.vroomvroom.config.security.accessDeniedHandler;

import com.backend.vroomvroom.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.backend.vroomvroom.common.exception.ErrorCode.ROLE_NOT_EXISTS;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        throw new CommonException(ROLE_NOT_EXISTS, "해당 계정은 권한이 없습니다. 테스트");
    }
}
