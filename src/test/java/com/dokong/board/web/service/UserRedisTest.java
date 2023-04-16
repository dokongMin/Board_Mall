//package com.dokong.board.web.service;
//
//import com.dokong.board.domain.user.User;
//import com.dokong.board.repository.user.UserRedisRepository;
//import com.dokong.board.web.dto.userdto.UserDtoRedis;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.annotation.Rollback;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class UserRedisTest {
//
//    @Autowired
//    private UserRedisRepository userRedisRepository;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Test
//    public void redis () throws Exception{
//        UserDtoRedis userRedis = UserDtoRedis.builder()
//                .username("aaa")
//                .build();
//
//        UserDtoRedis savedUserRedis = userRedisRepository.save(userRedis);
//
//        UserDtoRedis userDtoRedis = userRedisRepository.findById(savedUserRedis.getId()).get();
//        assertThat(userDtoRedis.getUsername()).isEqualTo("aaa");
//    }
//
//    @Test
//    public void testHash () throws Exception{
//        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
//        String key = "hashKey";
//
//        hashOperations.put(key, "hello", "world");
//
//        hashOperations.get(key, "hello");
//
//    }
//}
