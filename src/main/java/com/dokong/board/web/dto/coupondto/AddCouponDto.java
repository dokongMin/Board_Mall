package com.dokong.board.web.dto.coupondto;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCouponDto {

    private String couponName;
    private int couponRate;
    private String couponDetail;
    private int minCouponPrice;
    private CouponStatus couponStatus;

    private User user;

    @Builder
    public AddCouponDto(String couponName, int couponRate, String couponDetail, int minCouponPrice, CouponStatus couponStatus, User user) {
        this.couponName = couponName;
        this.couponRate = couponRate;
        this.couponDetail = couponDetail;
        this.minCouponPrice = minCouponPrice;
        this.couponStatus = CouponStatus.UNUSED;
    }

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(couponName)
                .couponRate(couponRate)
                .couponDetail(couponDetail)
                .minCouponPrice(minCouponPrice)
                .couponStatus(couponStatus)
                .build();
    }
}
