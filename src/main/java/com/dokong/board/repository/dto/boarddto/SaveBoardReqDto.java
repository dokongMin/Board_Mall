package com.dokong.board.repository.dto.boarddto;

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

    public Board toEntity(SaveBoardReqDto board) {
        return Board.builder()
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .likeCount(0)
                .build();
    }
}
