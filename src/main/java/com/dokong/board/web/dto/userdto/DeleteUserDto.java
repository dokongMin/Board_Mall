package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteUserDto {

    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;

    @Builder
    public DeleteUserDto(String username, String password, String name, String phoneNumber, String email, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }

    public User toEntity() {
        return User.builder()
                .username(null)
                .password(null)
                .name(null)
                .phoneNumber(null)
                .email(null)
                .gender(null)
                .build();
    }
}
