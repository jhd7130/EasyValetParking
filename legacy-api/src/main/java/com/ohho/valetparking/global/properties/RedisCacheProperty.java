package com.ohho.valetparking.global.properties;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RedisCacheProperty {

  @Value("${spring.redis.cache.host}")
  private String host;

  @Value("${spring.redis.cache.port}")
  private int port;

}
