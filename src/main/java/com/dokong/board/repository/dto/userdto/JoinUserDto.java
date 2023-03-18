package com.dokong.board.repository.dto.userdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.User;
import lombok.*;

import javax.persistence.Embedded;

@Getter
@Setter
@NoArgsConstructor
public class JoinUserDto {

    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    private Address address;

    @Builder
    public JoinUserDto(String username, String password, String name, String phoneNumber, String email, String gender, Address address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .gender(gender)
                .address(address)
                .build();
    }
}
