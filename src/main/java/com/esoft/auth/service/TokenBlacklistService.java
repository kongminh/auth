package com.esoft.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    private RedisTemplate redisTemplate;
    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

    public TokenBlacklistService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token) {
        String blacklistKey = BLACKLIST_KEY_PREFIX + token;
        redisTemplate.opsForValue().set(blacklistKey, true);
    }

    public boolean isTokenBlacklisted(String token) {
        String blacklistKey = BLACKLIST_KEY_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(blacklistKey));
    }
}
