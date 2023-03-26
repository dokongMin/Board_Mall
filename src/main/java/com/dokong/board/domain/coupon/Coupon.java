package com.dokong.board.domain.coupon;


import com.dokong.board.domain.user.User;
import com.dokong.board.domain.baseentity.BaseTimeEntity;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void updateCouponStatus(CouponStatus couponStatus) {
        this.couponStatus = couponStatus;
    }

    @Builder
    public Coupon(String couponName, int couponRate, String couponDetail, int minCouponPrice, CouponStatus couponStatus) {
        this.couponName = couponName;
        this.couponRate = couponRate;
        this.couponDetail = couponDetail;
        this.minCouponPrice = minCouponPrice;
        this.couponStatus = couponStatus;
    }
}
