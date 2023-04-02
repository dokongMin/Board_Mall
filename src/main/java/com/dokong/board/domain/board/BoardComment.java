package com.dokong.board.domain.board;


import com.dokong.board.domain.user.User;
import com.dokong.board.domain.baseentity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardCommentId")
    private Long id;

    private String commentContent;

    @Enumerated(EnumType.STRING)
    private BoardCommentStatus boardCommentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setBoard(Board board) {
        this.board = board;
    }

    public void writeComment(User user, Board board) {
        this.user = user;
        user.getBoardComments().add(this);
        this.board = board;
        board.getBoardComments().add(this);
    }

    public void updateBoardComment(String commentContent) {
        this.commentContent = commentContent;
    }

    public void deleteComment() {
        this.boardCommentStatus = BoardCommentStatus.DELETED;
    }

    @Builder
    public BoardComment(String commentContent, BoardCommentStatus boardCommentStatus) {
        this.commentContent = commentContent;
        this.boardCommentStatus = boardCommentStatus;
    }
}
