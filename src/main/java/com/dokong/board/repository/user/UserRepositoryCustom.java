package com.dokong.board.repository.user;

import com.dokong.board.web.dto.userdto.SearchUserDto;
import com.dokong.board.web.dto.userdto.UserSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {

    List<SearchUserDto> search(UserSearchCondition condition);
//    Page<SearchUserDto> searchPageSimple(UserSearchCondition condition, Pageable pageable); // 단순 쿼리
    Page<SearchUserDto> searchPageComplex(UserSearchCondition condition, Pageable pageable); // count 쿼리와 아닌걸 분리
}
