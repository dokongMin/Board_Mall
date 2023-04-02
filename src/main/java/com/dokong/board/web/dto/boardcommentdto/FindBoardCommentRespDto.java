package com.dokong.board.web.dto.boardcommentdto;

import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.board.BoardCommentStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindBoardCommentRespDto {

    private Long userId;
    private String username;
    private Long boardId;
    private String commentContent;
    private BoardCommentStatus boardCommentStatus;

    public static FindBoardCommentRespDto of(BoardComment boardComment) {
        return FindBoardCommentRespDto.builder()
                .userId(boardComment.getUser().getId())
                .username(boardComment.getUser().getUsername())
                .boardId(boardComment.getBoard().getId())
                .commentContent(boardComment.getCommentContent())
                .boardCommentStatus(boardComment.getBoardCommentStatus())
                .build();
    }

}
