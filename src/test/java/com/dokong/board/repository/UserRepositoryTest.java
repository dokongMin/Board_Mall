package com.dokong.board.repository;

import com.dokong.board.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void memberSave () throws Exception{
        // given
        User user = User.builder()
                .username("정민환")
                .password("1234")
                .build();
        // when
        User savedUser = userRepository.save(user);
        // then
        Assertions.assertThat(savedUser).isEqualTo(user);
     }
}