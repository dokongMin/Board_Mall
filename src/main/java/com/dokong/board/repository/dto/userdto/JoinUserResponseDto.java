package com.dokong.board.repository.dto.userdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinUserResponseDto {

    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    private Address address;
    private UserRole userRole;

    @Builder
    public JoinUserResponseDto(String username, String password, String name, String phoneNumber, String email, String gender, Address address, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.userRole = userRole;
    }

    public static JoinUserResponseDto of(User user) {
        return JoinUserResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .gender(user.getGender())
                .address(user.getAddress())
                .userRole(user.getUserRole())
                .build();
    }
}
