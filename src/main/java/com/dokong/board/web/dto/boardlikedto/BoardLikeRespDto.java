package com.dokong.board.web.dto.boardlikedto;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.user.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLikeRespDto {

    private Long userId;
    private String username;
    private Long boardId;
    private String boardTitle;
    private Long boardLikeId;
    private String errorDescription;


    public static BoardLikeRespDto of(User user, Board board, BoardLike boardLike) {
        return BoardLikeRespDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .boardId(board.getId())
                .boardTitle(board.getBoardTitle())
                .boardLikeId(boardLike.getId())
                .build();
    }
}
