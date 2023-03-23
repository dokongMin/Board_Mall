package com.dokong.board.web.dto.boardcommentdto;

import com.dokong.board.domain.board.BoardComment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentDto {

    private String commentContent;

    @Builder
    public BoardCommentDto(String commentContent) {
        this.commentContent = commentContent;
    }

    public BoardComment toEntity() {
        return BoardComment.builder()
                .commentContent(commentContent)
                .build();
    }

    public static BoardCommentDto of(BoardComment boardComment) {
        return BoardCommentDto.builder()
                .commentContent(boardComment.getCommentContent())
                .build();
    }
}
