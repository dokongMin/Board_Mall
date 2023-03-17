package com.dokong.board.domain;


import com.dokong.board.domain.baseentity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponId")
    private Long id;

    private String couponName;
    private int couponRate;
    private String couponDetail;
    private int minCouponPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Builder
    public Coupon(String couponName, int couponRate, String couponDetail, int minCouponPrice) {
        this.couponName = couponName;
        this.couponRate = couponRate;
        this.couponDetail = couponDetail;
        this.minCouponPrice = minCouponPrice;
    }
}
