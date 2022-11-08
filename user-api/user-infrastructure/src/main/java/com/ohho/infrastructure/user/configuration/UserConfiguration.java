package com.ohho.infrastructure.user.configuration;


import com.ohho.application.user.UserManager;
import com.ohho.application.user.processor.UserCreateProcessor;
import com.ohho.domain.user.UserRepository;
import com.ohho.infrastructure.user.UserInMemory;
import com.ohho.infrastructure.user.UserRepositoryAdaptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  @Bean
  public UserManager userManager(){
    return new UserManager(userCreateProcessor());
  }

  @Bean
  public UserCreateProcessor userCreateProcessor(){
    return new UserCreateProcessor(userRepository());
  }

  @Bean
  public UserRepository userRepository(){
    return new UserRepositoryAdaptor(new UserInMemory());
  }

}
