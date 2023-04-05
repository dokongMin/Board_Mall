package com.dokong.board.repository.user;

import com.dokong.board.domain.user.User;
import com.dokong.board.repository.user.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUsername(String username);

    @Query("select m from member m")
    List<User> findAllUser();

}
