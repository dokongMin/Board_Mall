package com.dokong.board.web.dto.boarddto;

import com.dokong.board.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveBoardRespDto {

    private Long id;
    private String boardTitle;
    private String boardContent;
    private long likeCount;

    @Builder
    public SaveBoardRespDto(String boardTitle, String boardContent, long likeCount, Long id) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.likeCount = likeCount;
    }

    public static SaveBoardRespDto of(Board board) {
        return SaveBoardRespDto.builder()
                .id(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .likeCount(0)
                .build();
    }
}
