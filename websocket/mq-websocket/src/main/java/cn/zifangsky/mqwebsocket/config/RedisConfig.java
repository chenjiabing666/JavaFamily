package cn.zifangsky.mqwebsocket.config;

import cn.zifangsky.mqwebsocket.mq.MessageReceiver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/**
 * Redis相关配置
 *
 * @author zifangsky
 * @date 2018/7/30
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisConfig {

    @Value("${spring.redis.timeout}")
    private String timeOut;

    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.message.topic-name}")
    private String topicName;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWait);

        return config;
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList(nodes));
        configuration.setMaxRedirects(maxRedirects);

        return configuration;
    }

    /**
     * JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration configuration,JedisPoolConfig jedisPoolConfig){
        return new JedisConnectionFactory(configuration,jedisPoolConfig);
    }

    /**
     * 使用Jackson序列化对象
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        return serializer;
    }

    /**
     * RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        //字符串方式序列化KEY
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //JSON方式序列化VALUE
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * 消息监听器
     */
    @Bean
    MessageListenerAdapter messageListenerAdapter(MessageReceiver messageReceiver, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer){
        //消息接收者以及对应的默认处理方法
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(messageReceiver, "receiveMessage");
        //消息的反序列化方式
        messageListenerAdapter.setSerializer(jackson2JsonRedisSerializer);

        return messageListenerAdapter;
    }

    /**
     * message listener container
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory
            , MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //添加消息监听器
        container.addMessageListener(messageListenerAdapter, new PatternTopic(topicName));

        return container;
    }

}
