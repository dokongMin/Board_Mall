package com.dokong.board.domain.board;

import com.dokong.board.domain.User;
import com.dokong.board.domain.baseentity.BaseEntity;
import com.dokong.board.domain.baseentity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    private String boardTitle;
    private String boardContent;
    private long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoardComments(BoardComment boardComment) {
        this.boardComments.add(boardComment);
        boardComment.setBoard(this);
    }

    public void setBoardLikes(BoardLike boardLike) {
        this.boardLikes.add(boardLike);
        boardLike.setBoard(this);
    }

}
