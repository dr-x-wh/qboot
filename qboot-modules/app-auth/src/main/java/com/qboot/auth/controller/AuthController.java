package com.qboot.auth.controller;

import com.qboot.cache.redis.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final RedisCache redisCache;

    @PostMapping("/login")
    public String login() {
        Optional<String> value = redisCache.get("test");
        return value.orElse("null");
    }

}
