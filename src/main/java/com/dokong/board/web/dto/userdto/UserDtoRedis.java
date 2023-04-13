package com.dokong.board.web.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@RedisHash(value = "userredis", timeToLive = 30)
@AllArgsConstructor
@Builder
public class UserDtoRedis {

    @Id
    private Long id;
    private String username;

}
