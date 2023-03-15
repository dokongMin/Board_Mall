package com.dokong.board.domain;

import com.dokong.board.domain.baseentity.BaseTimeEntity;
import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CartProduct> cartProducts = new ArrayList<>();
}
