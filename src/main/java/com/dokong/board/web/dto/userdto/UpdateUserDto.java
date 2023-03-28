package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {

    @Length(min = 4, max = 15)
    @NotBlank
    private String password;
    @Email
    private String email;
    private Address address;

    @Builder
    public UpdateUserDto(String password, String email, Address address) {
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User toEntity() {
        return User.builder()
                .password(password)
                .email(email)
                .address(address)
                .build();
    }
}
