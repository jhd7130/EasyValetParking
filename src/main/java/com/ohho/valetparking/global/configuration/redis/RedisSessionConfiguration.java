package com.ohho.valetparking.global.configuration.redis;

import com.ohho.valetparking.global.properties.RedisSessionProperty;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
    @Value : Spring이 지원하는 의존성 주입 방법중 하나입니다.
    SpEL을 지원하며 application.properties의 속성값을 프로퍼티에 넣어줍니다.
    RedisConnectionFactory을 생성하여 스프링 세션을 레디스 서버로 연결시킵니다.
    RedisConnectionFactory는 Connection 객체를 생성하여 관리하는 인터페이스입니다.
    RedisConnection을 리턴합니다. RedisConnection은 Redis 서버와의 통신을 추상화합니다.
    Redis Connection Factory를 리턴해주는 라이브러리로 Lettuce를 선택하였습니다.
    비동기로 요청하기 때문에 높은성능을 가지기 때문입니다.
*/
// 토큰 저장소이기 때문에 refreshTokend의 expire 시간과 동일하게 설정했다.
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 604800)
@EnableTransactionManagement
@Configuration
@AllArgsConstructor
public class RedisSessionConfiguration {

  private final RedisSessionProperty redisSessionProperty;
  /**
   Lettuce : Multi-Thread 에서 Thread-Safe 한 Redis 클라이언트로 netty 에 의해 관리된다.
   Sentinel, Cluster, Redis data model 같은 고급 기능들을 지원하며
   비동기 방식으로 요청하기에 TPS/CPU/Connection 개수와 응답속도 등 전 분야에서 Jedis 보다 뛰어나다.
   스프링 부트의 기본 의존성은 현재 Lettuce 로 되어있다.

   Jedis  : Multi-Thread 에서 Thread-unsafe 하며 Connection pool 을 이용해 멀티쓰레드 환경을 구성한다.
   Jedis 인스턴스와 연결할 때마다 Connection pool 을 불러오고 스레드 갯수가
   늘어난다면 시간이 상당히 소요될 수 있다.
   */
  @Bean({"redisSessionConnectionFactory", "redisConnectionFactory"})
  public RedisConnectionFactory redisSessionConnectionFactory() {
    return new LettuceConnectionFactory(redisSessionProperty.getHost(),
        redisSessionProperty.getPort());
  }
  /**
   RedisTemplate: Redis data access code 를 간소화 하기 위해 제공되는 클래스이다.
   주어진 객체들을 자동으로 직렬화/역직렬화 하며 binary 데이터를 Redis 에 저장한다.
   기본설정은 JdkSerializationRedisSerializer 이다.

   StringRedisTemplate : RedisTemplated의 key valut type이 모두 String일때
   사용하는 클래스이다. 기본적인 작동은 RedisTemplate과 유사하다.

   StringRedisSerializer: binary 데이터로 저장되기 때문에 이를 String 으로 변환시켜주며(반대로도 가능)
   UTF-8 인코딩 방식을 사용한다.

   GenericJackson2JsonRedisSerializer: 객체를 json 타입으로 직렬화/역직렬화를 수행한다.
   => 기본 StringRedisSerializer 를 사용하면 serialized 오류가 발생한다.

   setEnableTransactionSupport(Ture)
   Redis는 기본적으로 트랜잭션이 적용되지않는게 default이다. @Transactional 관리영역에 포함되지 않는다.

   */
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
    redisTemplate.setEnableTransactionSupport(true);

    return redisTemplate;

  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}
