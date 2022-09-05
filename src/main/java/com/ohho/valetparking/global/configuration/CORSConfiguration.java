package com.ohho.valetparking.global.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * client 브라우저의 Cors관련 헤더 Spring에서 제공해주는 기능
 * 우선적으로 프론트엔드 개발자 로컬 ip 주소를 등록 한다.
 */
@Configuration
@EnableWebMvc
public class CORSConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                // .allowedOrigins("*") //  이거 업데이트 됩 allowCredentials(true)과 함께 못쓴다.
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true).maxAge(3600);

        // Add more mappings...
    }
}
