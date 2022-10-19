package com.ohho.valetparking.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Role : Responsibility : Cooperation with :
 **/

@Component
@Getter
public class RedisSessionProperty {

  @Value("${spring.redis.host}")
  private String host;
  @Value("${spring.redis.port}")
  private int port;

}
