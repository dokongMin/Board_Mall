package com.dokong.board.web.service.redis;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Boolean> redisTemplate;
    private static final Long TIME_OUT = 86400L;

    public void setClientKey(String username, Long boardId) {
        String key = generateKey(username, boardId);
        redisTemplate.opsForValue().set(key, true, TIME_OUT, TimeUnit.SECONDS);
    }

    public boolean checkDuplicateRequest(String username, Long boardId) {
        String key = generateKey(username, boardId);
        if (redisTemplate.hasKey(key)) {
            return true;
        }
        setClientKey(username, boardId);
        return false;
    }

    private String generateKey(String username, Long boardId) {
        return username + " : "  + boardId;
    }
}
