package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserSearchCondition {

    private Long id;
    private String username;
    private String name;
    private UserRole userRole;
}
