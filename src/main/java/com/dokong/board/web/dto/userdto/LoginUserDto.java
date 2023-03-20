package com.dokong.board.web.dto.userdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDto {

    private String username;
    private String password;

    @Builder
    public LoginUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
