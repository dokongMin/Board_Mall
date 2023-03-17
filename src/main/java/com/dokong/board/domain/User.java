package com.dokong.board.domain;

import com.dokong.board.domain.baseentity.BaseTimeEntity;
import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
//        if (coupon.getUser() != this) {
//            coupon.setUser(this);
//        }
    }

//    public void likeBoard(BoardLike boardLike) {
//        this.boardLikes.add(boardLike);
//        if (boardLike.getUser() != this) {
//            boardLike.setUser(this);
//        }
//    }
//
//    public void writeComment(BoardComment boardComment) {
//        this.boardComments.add(boardComment);
//        boardComment.setUser(this);
//    }

    @Builder
    public User(String username, String password, String name, String phoneNumber, String email, String gender, Address address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
    }
}
