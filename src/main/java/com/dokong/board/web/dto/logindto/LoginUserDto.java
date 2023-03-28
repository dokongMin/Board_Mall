package com.dokong.board.web.dto.logindto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
