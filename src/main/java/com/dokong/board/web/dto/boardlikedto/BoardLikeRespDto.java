package com.dokong.board.web.dto.boardlikedto;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeRespDto {

    private Long userId;
    private String username;
    private Long boardId;
    private String boardTitle;
    private Long boardLikeId;
    private String errorDescription;

    @Builder
    public BoardLikeRespDto(Long userId, String username, Long boardId, String boardTitle, Long boardLikeId, String errorDescription) {
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardLikeId = boardLikeId;
    }

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
