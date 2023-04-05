package com.dokong.board.web.dto.boardlikedto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchBoardDto {

    private String boardTitle;
    private String username;

    @QueryProjection
    public SearchBoardDto(String boardTitle, String username) {
        this.boardTitle = boardTitle;
        this.username = username;
    }
}
