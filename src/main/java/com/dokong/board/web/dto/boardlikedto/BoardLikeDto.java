package com.dokong.board.web.dto.boardlikedto;

import com.dokong.board.domain.board.BoardLike;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeDto {

    private Long id;

    @Builder
    public BoardLikeDto(Long id) {
        this.id = id;
    }

    public BoardLike toEntity() {
        return BoardLike.builder().build();
    }
}
