package darkversum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Profile("Linux")
@Configuration
public class RedisConfig {

	@Bean
	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
		return new LettuceConnectionFactory("localhost", 6379);
	}

	@Bean
	public ReactiveRedisTemplate<String, String> reactiveRedisTemplateString
			(ReactiveRedisConnectionFactory connectionFactory) {
		return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
	}
}
