package com.dokong.board.repository.dto;

import com.dokong.board.domain.board.Board;
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

    public BoardComment toEntity(BoardCommentDto boardComment) {
        return BoardComment.builder()
                .commentContent(boardComment.getCommentContent())
                .build();
    }

    public static BoardCommentDto of(BoardComment boardComment) {
        return BoardCommentDto.builder()
                .commentContent(boardComment.getCommentContent())
                .build();
    }
}
