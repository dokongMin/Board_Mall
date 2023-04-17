package com.dokong.board.web.service.redis;


import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.user.User;
import com.dokong.board.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Boolean> redisTemplate;

    private final RedissonClient redissonClient;
    private final UserService userService;
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
