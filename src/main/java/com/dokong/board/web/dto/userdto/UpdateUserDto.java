package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {

    private String username;
    private String password;
    private String email;
    private Address address;

    @Builder
    public UpdateUserDto(String username, String password, String email, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .address(address)
                .build();
    }
}
