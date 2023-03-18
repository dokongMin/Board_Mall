package com.dokong.board.repository.dto.coupondto;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCouponResponseDto {

    private String couponName;
    private int couponRate;
    private String couponDetail;
    private int minCouponPrice;
    private CouponStatus couponStatus;

    @Builder
    public AddCouponResponseDto(String couponName, int couponRate, String couponDetail, int minCouponPrice, CouponStatus couponStatus) {
        this.couponName = couponName;
        this.couponRate = couponRate;
        this.couponDetail = couponDetail;
        this.minCouponPrice = minCouponPrice;
        this.couponStatus = couponStatus;
    }

    public static AddCouponResponseDto of(Coupon coupon) {
        return AddCouponResponseDto.builder()
                .couponName(coupon.getCouponName())
                .couponDetail(coupon.getCouponDetail())
                .couponRate(coupon.getCouponRate())
                .minCouponPrice(coupon.getMinCouponPrice())
                .couponStatus(coupon.getCouponStatus())
                .build();
    }
}