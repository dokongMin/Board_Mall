package com.dokong.board.domain.board;

import com.dokong.board.domain.User;
import com.dokong.board.domain.baseentity.BaseEntity;
import com.dokong.board.domain.baseentity.BaseTimeEntity;
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

    public void addLikeCount() {
        this.likeCount += 1;
    }
    public void removeLikeCount() {
        this.likeCount -= 1;
    }

    @Builder
    public Board(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.likeCount = 0;
    }
}
