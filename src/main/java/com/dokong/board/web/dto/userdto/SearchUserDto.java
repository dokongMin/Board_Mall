package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.UserRole;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchUserDto {

    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private UserRole userRole;

    @QueryProjection
    public SearchUserDto(String username, String name, String phoneNumber, String email, UserRole userRole) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRole = userRole;
    }
}
