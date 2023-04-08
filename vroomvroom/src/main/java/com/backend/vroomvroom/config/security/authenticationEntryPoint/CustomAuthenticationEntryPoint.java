package com.backend.vroomvroom.config.security.authenticationEntryPoint;

import com.backend.vroomvroom.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.backend.vroomvroom.common.exception.ErrorCode.UNAUTHORIZED;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        throw new CommonException(UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
    }
}
