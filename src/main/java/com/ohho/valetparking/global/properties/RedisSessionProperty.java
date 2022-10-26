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

  @Value("${spring.redis.session.host}")
  private String host;
  @Value("${spring.redis.session.port}")
  private int port;

}
