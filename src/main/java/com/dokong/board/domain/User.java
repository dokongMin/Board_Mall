package com.dokong.board.domain;

import com.dokong.board.domain.baseentity.BaseTimeEntity;
import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.order.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BoardLike> boardLikes = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CartProduct> cartProducts = new ArrayList<>();

    public void addCoupons(Coupon coupon) {
        this.coupons.add(coupon);
        if (coupon.getUser() != this) {
            coupon.setUser(this);
        }
    }

    public void writeBoard(Board board) {
        this.boards.add(board);
        if (board.getUser() != this) {
            board.setUser(this);
        }
    }

    public void likeBoard(BoardLike boardLike) {
        this.boardLikes.add(boardLike);
        if (boardLike.getUser() != this) {
            boardLike.setUser(this);
        }
    }

    public void writeComment(BoardComment boardComment) {
        this.boardComments.add(boardComment);
        boardComment.setUser(this);
    }
}
