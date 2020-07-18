package com.yang.springboot.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.yang.springboot.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class myRedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> myRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Employee> redisTemplate=new RedisTemplate<Object, Employee>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Employee.class);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
    @Bean
    public RedisCacheManager myRedisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter=RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        RedisSerializer redisSerializer=new GenericJackson2JsonRedisSerializer();
        /**
         * 使用FastJson会解决GenericJackson2JsonRedisSerializer对于没有set方法类的序列化失败情况
         */
        RedisSerializer redisSerializer=new GenericFastJsonRedisSerializer();
        RedisSerializationContext redisSerializationContext=RedisSerializationContext.fromSerializer(redisSerializer);
        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(redisSerializationContext.getValueSerializationPair());
        RedisCacheManager redisCacheManager=new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
        return redisCacheManager;
    }
}

