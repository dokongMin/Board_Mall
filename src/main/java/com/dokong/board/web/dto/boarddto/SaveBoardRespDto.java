package com.dokong.board.web.dto.boarddto;

import com.dokong.board.domain.board.Board;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveBoardRespDto {

    private Long id;
    private Long userId;
    private String boardTitle;
    private String boardContent;
    private long likeCount;


    public static SaveBoardRespDto of(Board board) {
        return SaveBoardRespDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .likeCount(0)
                .userId(board.getUser().getId())
                .build();
    }
}
