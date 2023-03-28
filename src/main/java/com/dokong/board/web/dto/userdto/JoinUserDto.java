package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class JoinUserDto {

    @Length(min = 4, max = 15)
    @NotBlank
    private String username;

    @Length(min = 6, max = 15)
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @Email
    private String email;
    private String gender;
    private Address address;
    private UserRole userRole;

    @Builder
    public JoinUserDto(String username, String password, String name, String phoneNumber, String email, String gender, Address address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.userRole = UserRole.BRONZE;
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
                .userRole(userRole)
                .build();
    }
}
