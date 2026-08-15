package com.qboot.cache.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * Redis string operations and explicitly typed JSON object caching.
 */
@RequiredArgsConstructor
public final class RedisCache {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static String requireKey(String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("Redis键不能为空");
        }
        return key;
    }

    private static String requireValue(String value) {
        return Objects.requireNonNull(value, "Redis值不能为空");
    }

    private static Object requireObject(Object value) {
        return Objects.requireNonNull(value, "Redis对象不能为空");
    }

    private static Duration requireTtl(Duration ttl) {
        Objects.requireNonNull(ttl, "Redis过期时间不能为空");
        if (ttl.isZero() || ttl.isNegative()) {
            throw new IllegalArgumentException("Redis过期时间必须大于0");
        }
        return ttl;
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(requireKey(key)));
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(requireKey(key), requireValue(value));
    }

    public void set(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(requireKey(key), requireValue(value), requireTtl(ttl));
    }

    public <T> Optional<T> getObject(String key, Class<T> type) {
        Objects.requireNonNull(type, "Redis对象类型不能为空");
        return get(key).map(json -> readJson(json, type));
    }

    public <T> Optional<T> getObject(String key, TypeReference<T> type) {
        Objects.requireNonNull(type, "Redis对象类型不能为空");
        return get(key).map(json -> readJson(json, type));
    }

    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(requireKey(key), writeJson(requireObject(value)));
    }

    public void setObject(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(requireKey(key), writeJson(requireObject(value)), requireTtl(ttl));
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(requireKey(key)));
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(requireKey(key)));
    }

    public boolean expire(String key, Duration ttl) {
        return Boolean.TRUE.equals(redisTemplate.expire(requireKey(key), requireTtl(ttl)));
    }

    public long increment(String key) {
        return increment(key, 1);
    }

    public long increment(String key, long delta) {
        Long result = redisTemplate.opsForValue().increment(requireKey(key), delta);
        return Objects.requireNonNull(result, "Redis自增操作未返回结果");
    }

    private String writeJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JacksonException exception) {
            throw new IllegalStateException("Redis对象序列化失败", exception);
        }
    }

    private <T> T readJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JacksonException exception) {
            throw new IllegalStateException("Redis对象反序列化失败", exception);
        }
    }

    private <T> T readJson(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JacksonException exception) {
            throw new IllegalStateException("Redis对象反序列化失败", exception);
        }
    }

}
