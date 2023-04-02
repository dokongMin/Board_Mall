package com.dokong.board.web.dto.boardcommentdto;

import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.board.BoardCommentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCommentDto {

    @NotNull
    private Long userId;
    private Long boardId;
    @NotBlank
    private String commentContent;
    private BoardCommentStatus boardCommentStatus;

    public BoardComment toEntity() {
        return BoardComment.builder()
                .commentContent(commentContent)
                .boardCommentStatus(BoardCommentStatus.CREATED)
                .build();
    }

    public static BoardCommentDto of(BoardComment boardComment) {
        return BoardCommentDto.builder()
                .userId(boardComment.getUser().getId())
                .boardId(boardComment.getBoard().getId())
                .commentContent(boardComment.getCommentContent())
                .boardCommentStatus(boardComment.getBoardCommentStatus())
                .build();
    }
}
