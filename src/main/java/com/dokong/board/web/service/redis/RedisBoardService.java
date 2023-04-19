package com.dokong.board.web.service.redis;


import com.dokong.board.domain.board.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisBoardService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final Long TIME_OUT = 86400L;

    private final Map<Long, Long> hash = new HashMap<>();

    public void setClientKey(String username, Long boardId) {
        String key = generateKeyForCheckDuplicate(username, boardId);
        redisTemplate.opsForValue().set(key, "true", TIME_OUT, TimeUnit.SECONDS);
    }

    public boolean checkDuplicateRequest(String username, Long boardId) {
        String key = generateKeyForCheckDuplicate(username, boardId);
        if (redisTemplate.hasKey(key)) {
            return true;
        }
        setClientKey(username, boardId);
        return false;
    }

    public boolean addViewCountInRedis(Board board) {
        String key = generateKeyForViewCount(board);
        redisTemplate.opsForHash().increment(key, board.getBoardTitle(), 1);
        return true;
    }

    public long sendViewCount(Board board) {
        String key = generateKeyForViewCount(board);
        if (redisTemplate.opsForHash().hasKey(key, board.getBoardTitle())) {
            Long viewCount = Long.parseLong(redisTemplate.opsForHash().get(key, board.getBoardTitle()).toString());
            redisTemplate.opsForHash().delete(key, board.getBoardTitle());
            return viewCount;
        }
        else {
            return 0;
        }
    }

    private String generateKeyForViewCount(Board board) {
        return board.getBoardTitle() + " : " + board.getId();
    }

    private String generateKeyForCheckDuplicate(String username, Long boardId) {
        return username + " : "  + boardId;
    }

}
