package com.dokong.board.web.dto.boardlikedto;

import com.dokong.board.domain.board.BoardLike;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLikeDto {

    private Long userId;
    private Long boardId;

    public BoardLike toEntity() {
        return BoardLike.builder()
                .build();
    }
}
