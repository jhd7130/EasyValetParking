package com.ohho.valetparking.global.configuration;

import com.ohho.valetparking.global.Interceptor.AuthLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthLoginInterceptor())
                .addPathPatterns("*")
                .excludePathPatterns("/member/*")
                .excludePathPatterns("/swagger-ui/*")
                .excludePathPatterns("/ticket");
    }
}
