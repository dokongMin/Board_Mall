package com.dokong.board.domain.user;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.CartProduct;
import com.dokong.board.domain.baseentity.BaseTimeEntity;
import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.order.Order;
import com.dokong.board.exception.NotEnoughTimeException;
import lombok.*;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

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

    public void addCoupon(Coupon coupon) {
        checkJoinTime();
        this.coupons.add(coupon);
        coupon.setUser(this);
    }

    private void checkJoinTime() {
        LocalDateTime joinDateTime = this.getCreatedDate();
        LocalDateTime now = LocalDateTime.now();
        long betweenTime = ChronoUnit.DAYS.between(joinDateTime, now);
        if (betweenTime < 1) {
            throw new NotEnoughTimeException("쿠폰은 회원 가입 후, 하루가 지나야 발급 가능합니다.");
        }
    }

    public void updateUser(String password, String email, Address address) {
        this.password = password;
        this.email = email;
        this.address = address;
    }

    @Builder
    public User(String username, String password, String name, String phoneNumber, String email, String gender, Address address, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.userRole = userRole;
    }
}
