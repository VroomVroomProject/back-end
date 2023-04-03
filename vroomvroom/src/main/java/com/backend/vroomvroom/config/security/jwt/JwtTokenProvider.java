package com.backend.vroomvroom.config.security.jwt;

import com.backend.vroomvroom.common.exception.CommonException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static com.backend.vroomvroom.common.exception.ErrorCode.INVALID_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private final UserDetailsService userDetailsService;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    //30분
    private long accessTokenTime = 1000L * 60 * 30;

    //7일
    private long refreshTokenTime = 1000L * 60 * 60 * 24 * 7;

    @Override
    public void afterPropertiesSet() throws Exception {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * throw ExpiredJwtException => 토큰이 만료된 경우
     * throw JwtException => 토큰이 유효하지 않은 경우(변경된 경우)
     */
    public String getUserId(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new CommonException(INVALID_REQUEST, "토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new CommonException(INVALID_REQUEST, "유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            throw new CommonException(INVALID_REQUEST, "유효하지 않은 토큰입니다.");
        }

    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateTokenExpiration(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * accessToken 만료 여부에 상관없이 subject 반환
     */
    public String getUserIdByExpiredToken(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (Exception e) {
            throw new CommonException(INVALID_REQUEST, "유효하지 않은 토큰입니다.");
        }
    }

}
