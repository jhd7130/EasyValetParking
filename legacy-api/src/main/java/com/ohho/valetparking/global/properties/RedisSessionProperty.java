package com.ohho.valetparking.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Role : 여러개의 프로퍼티가 사용될 수 있습니다. 따라서 프로퍼티를 객체로 만들어 놓고 제공합니다.
 **/

@Component
@Getter
public class RedisSessionProperty {

  @Value("${spring.redis.session.host}")
  private String host;
  @Value("${spring.redis.session.port}")
  private int port;

}
