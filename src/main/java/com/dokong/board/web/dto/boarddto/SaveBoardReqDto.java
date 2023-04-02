package com.dokong.board.web.dto.boarddto;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardStatus;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveBoardReqDto {

    @Length(min = 2, max = 20)
    @NotBlank
    private String boardTitle;
    @Length(min = 2)
    @NotBlank
    private String boardContent;
    private long likeCount;
    private BoardStatus boardStatus;
    @NotNull
    private Long userId;


    public Board toEntity() {
        return Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .likeCount(0)
                .boardStatus(BoardStatus.CREATED)
                .build();
    }
}
