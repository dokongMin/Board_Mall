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
public class DeleteBoardRespDto {

    private Long id;
    private String boardTitle;
    private String boardContent;
    private BoardStatus boardStatus;

    public static DeleteBoardRespDto of(Board board) {
        return DeleteBoardRespDto.builder()
                .id(board.getId())
                .boardContent(board.getBoardContent())
                .boardTitle(board.getBoardTitle())
                .boardStatus(board.getBoardStatus())
                .build();
    }
}
