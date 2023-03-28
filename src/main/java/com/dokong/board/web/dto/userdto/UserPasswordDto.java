package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordDto {

    private String password;

    public static UserPasswordDto of(User user) {
        return UserPasswordDto.builder()
                .password(user.getPassword())
                .build();
    }
}
