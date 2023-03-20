package com.dokong.board.web.dto.boarddto;

import com.dokong.board.domain.board.Board;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SaveBoardReqDto {

    private String boardTitle;
    private String boardContent;
    private long likeCount;

    @Builder
    public SaveBoardReqDto(String boardTitle, String boardContent, long likeCount) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.likeCount = likeCount;
    }

    public Board toEntity() {
        return Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .likeCount(0)
                .build();
    }
}
