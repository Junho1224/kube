package com.von.api.common.component.interceptor;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.von.api.common.component.security.JwtProvider;
import com.von.api.user.model.User;
import com.von.api.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final UserRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // String token = jwtProvider.extractTokenFromHeader(request);
        // log.info("1- 인터셉터 토큰 로그 Bearer 포함 : {}", token);

        // if (token.equals("undefined")) {
        // response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        // return false;
        // }

        // Long id = jwtProvider.getPayload(token).get("userId", Long.class); // 클레임에서
        // 가져올 때 class에서 뽑아와야함 내가 넣은 이름. //
        // // Long.class 디스크 정적 객체?
        // log.info("2 - 인터셉터 사용자 id : {}", id);

        // Optional<User> user = repository.findById(id);
        // log.info("3 - 인터셉터 사용자 정보 : {}", user);

        // if (!user.isPresent()) {
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        // return false;
        // }

        // log.info("4 - 인터셉터 최종 {}", true);

        return Stream.of(request)
                .map(i -> jwtProvider.extractTokenFromHeader(i))
                .filter(token -> !token.equals("undefined"))
                .peek(token -> log.info("1- 인터셉터 토큰 로그 Bearer 포함 : {}", token))
                .map(token -> jwtProvider.getPayload(token).get("userId", Long.class))
                .map(id -> repository.findById(id))
                .filter(user -> user.isPresent())
                .peek(user -> log.info("2- 인터셉터 사용자 ID : {}", user))
                .findFirst()
                .isPresent();

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) // Exception
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
