package com.dokong.board.domain.board;

import com.dokong.board.domain.user.User;
import com.dokong.board.domain.baseentity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    private String boardTitle;
    private String boardContent;
    private long likeCount;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes = new ArrayList<>();

    public void writeBoard(User user) {
        this.user = user;
        user.getBoards().add(this);
    }

    public void pushBoardLike(User user, BoardLike boardLike) {
        user.getBoardLikes().add(boardLike);
        boardLikes.add(boardLike);
        boardLike.setUser(user);
        boardLike.setBoard(this);
        addLikeCount();
    }

    public void cancelBoardLike(BoardLike boardLike, User user, Board board) {
        removeLikeCount();
        user.getBoardLikes().remove(boardLike);
        board.getBoardLikes().remove(boardLike);
    }


    public void addLikeCount() {
        this.likeCount += 1;
    }
    public void removeLikeCount() {
        this.likeCount -= 1;
    }

    public void updateBoard(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void deleteBoard() {
        this.boardStatus = BoardStatus.DELETED;
    }

    @Builder
    public Board(String boardTitle, String boardContent, long likeCount, BoardStatus boardStatus) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.likeCount = likeCount;
        this.boardStatus = boardStatus;
    }
}
