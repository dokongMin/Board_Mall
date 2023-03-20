package com.dokong.board.web.dto.userdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionUserDto {

    private Long id;
    private String username;

    @Builder
    public SessionUserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }


}
