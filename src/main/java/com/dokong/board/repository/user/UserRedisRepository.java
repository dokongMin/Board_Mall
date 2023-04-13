package com.dokong.board.repository.user;

import com.dokong.board.domain.user.User;
import com.dokong.board.web.dto.userdto.UserDtoRedis;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<UserDtoRedis, Long> {
}
