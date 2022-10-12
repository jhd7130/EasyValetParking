package com.ohho.valetparking.global.configuration;

import com.ohho.valetparking.global.Interceptor.AuthLoginInterceptor;
import com.ohho.valetparking.global.Interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

  private final AuthLoginInterceptor authLoginInterceptor;
  private final PermissionInterceptor permissionInterceptor;


  public InterceptorConfiguration(AuthLoginInterceptor authLoginInterceptor,
      PermissionInterceptor permissionInterceptor) {
    assert authLoginInterceptor != null;
    assert permissionInterceptor != null;
    this.authLoginInterceptor = authLoginInterceptor;
    this.permissionInterceptor = permissionInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authLoginInterceptor)
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/member/*")
        .excludePathPatterns("/member")
        .excludePathPatterns("/swagger-ui/*")
        .excludePathPatterns("/ticket")
    ;
    registry.addInterceptor(permissionInterceptor)
        .order(2)
        .addPathPatterns("/**")
        .excludePathPatterns("/member/*")
        .excludePathPatterns("/member")
        .excludePathPatterns("/swagger-ui/*")
        .excludePathPatterns("/ticket")
    ;
  }


}
