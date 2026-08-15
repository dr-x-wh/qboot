package com.qboot.cache.config;

import com.qboot.cache.redis.RedisCache;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration(after = {DataRedisAutoConfiguration.class, JacksonAutoConfiguration.class})
@ConditionalOnClass({StringRedisTemplate.class, ObjectMapper.class})
@ConditionalOnSingleCandidate(StringRedisTemplate.class)
public class CacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(ObjectMapper.class)
    RedisCache redisCache(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        return new RedisCache(redisTemplate, objectMapper);
    }

}
