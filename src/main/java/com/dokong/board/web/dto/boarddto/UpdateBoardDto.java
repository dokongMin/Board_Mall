package com.dokong.board.web.dto.boarddto;
import com.dokong.board.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBoardDto {

    private String boardTitle;
    private String boardContent;

    @Builder
    public UpdateBoardDto(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public Board toEntity(UpdateBoardDto boardDto) {
        return Board.builder()
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .build();
    }

    public static UpdateBoardDto of(Board board) {
        return UpdateBoardDto.builder()
                .boardContent(board.getBoardContent())
                .boardTitle(board.getBoardTitle())
                .build();
    }

}
