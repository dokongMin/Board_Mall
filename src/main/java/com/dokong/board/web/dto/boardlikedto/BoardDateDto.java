package com.dokong.board.web.dto.boardlikedto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardDateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @Builder
    public BoardDateDto(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
