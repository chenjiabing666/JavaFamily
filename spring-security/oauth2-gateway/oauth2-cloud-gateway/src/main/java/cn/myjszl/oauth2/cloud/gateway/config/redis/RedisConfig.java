package cn.myjszl.oauth2.cloud.gateway.config.redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
	@Value("${spring.redis.host:127.0.0.1}")
	private String host;

	@Value("${spring.redis.port:6379}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;

	@Value("${spring.redis.database:0}")
	private int database;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		if (StringUtils.isNotBlank(password)) {
			return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
		} else {
			return new JedisPool(jedisPoolConfig, host, port, timeout, null, database);
		}
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getDeclaringClass().getName());
				Arrays.stream(params).map(Object::toString).forEach(sb::append);
				return sb.toString();
			}
		};
	}



	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		// 设置序列化
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper mapper = new ObjectMapper();
//		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(mapper);
		// 配置redisTemplate
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		RedisSerializer<?> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);// key序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
		redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}


	class JacksonRedisSerializer<T> implements RedisSerializer<T> {
		private Class<T> clazz;
		private ObjectMapper mapper;

		JacksonRedisSerializer(Class<T> clazz) {
			super();
			this.clazz = clazz;
			this.mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		}

		@Override
		public byte[] serialize(T t) throws SerializationException {
			try {
				return mapper.writeValueAsBytes(t);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public T deserialize(byte[] bytes) throws SerializationException {
			if (bytes.length <= 0) {
				return null;
			}
			try {
				return mapper.readValue(bytes, clazz);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}


