package net.chinahrd.common.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis的配置
 * 
 * @author guanjian
 *
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    // 缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheManager cacheManager = RedisCacheManager.create(factory);
        return cacheManager;
    }

    // @Bean
    // public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory
    // factory) {
    // RedisTemplate<Object, Object> template = new RedisTemplate<Object,
    // Object>();
    // template.setConnectionFactory(factory);
    // template.setKeySerializer(new StringRedisSerializer());
    // template.setValueSerializer(new RedisObjectSerializer());
    // return template;
    // }

    // @Bean
    // public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory
    // redisConnectionFactory) {
    // RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    // redisTemplate.setConnectionFactory(redisConnectionFactory);
    //
    // // 使用Jackson2JsonRedisSerialize 替换默认序列化
    // Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new
    // Jackson2JsonRedisSerializer(Object.class);
    //
    // ObjectMapper objectMapper = new ObjectMapper();
    // objectMapper.setVisibility(PropertyAccessor.ALL,
    // JsonAutoDetect.Visibility.ANY);
    // objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    //
    // jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    //
    // // 设置value的序列化规则和 key的序列化规则
    // redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    // redisTemplate.setKeySerializer(new StringRedisSerializer());
    // redisTemplate.afterPropertiesSet();
    // return redisTemplate;
    // }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 全局开启AutoType，不建议使用
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("net.chinahrd.");

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
