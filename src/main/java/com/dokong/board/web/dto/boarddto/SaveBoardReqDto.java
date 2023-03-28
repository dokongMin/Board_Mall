package com.dokong.board.web.dto.boarddto;

import com.dokong.board.domain.board.Board;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SaveBoardReqDto {

    @Length(min = 2, max = 20)
    @NotBlank
    private String boardTitle;
    @Length(min = 2)
    @NotBlank
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
