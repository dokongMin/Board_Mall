package com.dokong.board.domain.board;

import com.dokong.board.domain.user.User;
import com.dokong.board.domain.baseentity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardLikeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * 두 번 클릭 시 , 좋아요 취소 기능 구현하기
     */
    public void pushLike(User user, Board board) {
//        if (user.getBoardLikes().contains(this)) {
//            System.out.println("호출됨?");
//        }
        this.user = user;
        user.getBoardLikes().add(this);
        this.board = board;
        board.getBoardLikes().add(this);
        board.addLikeCount();
    }

}
