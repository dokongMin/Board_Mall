package com.dokong.board.web.dto.logindto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
