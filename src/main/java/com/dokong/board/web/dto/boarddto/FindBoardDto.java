package com.dokong.board.web.dto.boarddto;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindBoardDto {

    private Long boardId;

    private String boardTitle;
    private String boardContent;
    private Long likeCount;
    private BoardStatus boardStatus;

    public static FindBoardDto of(Board board) {
        return FindBoardDto.builder()
                .boardId(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .likeCount(board.getLikeCount())
                .boardStatus(board.getBoardStatus())
                .build();
    }
}
