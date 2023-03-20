package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserResponseDto {

    private String username;
    private String password;
    private String email;
    private Address address;

    @Builder
    public UpdateUserResponseDto(String username, String password, String email, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public static UpdateUserResponseDto of (User user) {
        return UpdateUserResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }
}
