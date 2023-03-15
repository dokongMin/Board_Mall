package com.dokong.board.domain;


import com.dokong.board.domain.baseentity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponId")
    private Long id;

    private String couponName;
    private Long couponRate;
    private String couponDetail;
    private Long minCouponPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
//        if (this.user != null) {
//            this.user.getCoupons().remove(this);
//        }
//        this.user = user;
//        if (!user.getCoupons().contains(this)) {
//            user.addCoupons(this);
//        }
    }
}
