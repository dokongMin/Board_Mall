package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.UserRole;
import lombok.Data;

@Data
public class UserSearchCondition {

    private Long id;
    private String username;
    private String name;
    private UserRole userRole;
}
