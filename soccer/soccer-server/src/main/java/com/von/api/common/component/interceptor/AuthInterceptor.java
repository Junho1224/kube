package com.von.api.common.component.interceptor;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.von.api.common.component.security.JwtProvider;
import com.von.api.user.model.User;
import com.von.api.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor{

    private final JwtProvider jwtProvider;
    private final UserRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

                String token = jwtProvider.extractTokenFromHeader(request);

                if(ObjectUtils.isEmpty(token)){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                String strid = jwtProvider.getPayload(token);
                Long id = Long.parseLong(strid);

                Optional<User> user = repository.findById(id);

                if(ObjectUtils.isEmpty(user)){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
        return true; //만약에 안맞으면 반납 하기 위해서 true?
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) //Exception
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
