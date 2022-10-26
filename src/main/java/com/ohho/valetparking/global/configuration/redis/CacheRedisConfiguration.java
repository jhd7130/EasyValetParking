package com.ohho.valetparking.global.configuration.redis;

import com.ohho.valetparking.global.properties.RedisCacheProperty;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Value : Spring이 지원하는 의존성 주입 방법중 하나입니다. SpEL을 지원하며 application.properties의 속성값을 프로퍼티에 넣어줍니다.
 * RedisConnectionFactory을 생성하여 스프링 세션을 레디스 서버로 연결시킵니다. RedisConnectionFactory는 Connection 객체를 생성하여
 * 관리하는 인터페이스입니다. RedisConnection을 리턴합니다. RedisConnection은 Redis 서버와의 통신을 추상화합니다. Redis Connection
 * Factory를 리턴해주는 라이브러리로 Lettuce를 선택하였습니다. 비동기로 요청하기 때문에 높은성능을 가지기 때문입니다.
 */

// 토큰 저장소이기 때문에 refreshTokend의 expire 시간과 동일하게 설정했다.
//@EnableTransactionManagement : 를 사용하면 트랜잭션이 무조건 동작한다. 설정할 수 있는 flag가 있는지 봐야겠다.
@EnableCaching
@Configuration
@AllArgsConstructor
public class CacheRedisConfiguration {

  private final RedisCacheProperty redisCacheProperty;

  @Bean
  public RedisConnectionFactory redisCacheConnectionFactory() {
    return new LettuceConnectionFactory(redisCacheProperty.getHost(),
        redisCacheProperty.getPort());
  }

  @Bean
  public CacheManager redisCacheManager() {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
            new GenericJackson2JsonRedisSerializer())).prefixCacheNameWith("Caching :").entryTtl(
            Duration.ofDays(1));

    RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
            redisCacheConnectionFactory()).cacheDefaults(redisCacheConfiguration).
        transactionAware().build();
    return redisCacheManager;
  }

}
