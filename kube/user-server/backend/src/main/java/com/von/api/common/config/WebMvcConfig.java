package com.von.api.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import com.von.api.common.component.interceptor.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer{

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // interceptor.setParamName("locale");
        registry.addInterceptor(authInterceptor)
        .addPathPatterns("/api/**")
        // .addPathPatterns("/api/users/**")
        .excludePathPatterns("/api/auth/**","/api/boards/**","/api/articles/**");
        
        
        

    }

   

    
}
