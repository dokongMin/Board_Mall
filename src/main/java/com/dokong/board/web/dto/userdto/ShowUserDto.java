package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowUserDto {

    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private String userRole;

    @Builder
    public ShowUserDto(String username, String name, String phoneNumber, String email, String userRole) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRole = userRole;
    }

    public static ShowUserDto of(User user) {
        return ShowUserDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userRole(user.getUserRole().getUserRoleDescription())
                .build();
    }
}
