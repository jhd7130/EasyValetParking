package com.ohho.valetparking.global.configuration.redis;

import com.ohho.valetparking.global.properties.RedisSessionProperty;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@Configuration
@AllArgsConstructor
public class RedisSessionConfiguration {

  private final RedisSessionProperty redisSessionProperty;

  @Bean({"redisSessionConnectionFactory", "redisConnectionFactory"})
  public RedisConnectionFactory redisSessionConnectionFactory() {
    return new LettuceConnectionFactory(redisSessionProperty.getHost(),
        redisSessionProperty.getPort());
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisSessionConnectionFactory());

    redisTemplate.setKeySerializer(stringRedisSerializer);
    redisTemplate.setHashKeySerializer(stringRedisSerializer);
    redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
    redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

    return redisTemplate;
  }
}
