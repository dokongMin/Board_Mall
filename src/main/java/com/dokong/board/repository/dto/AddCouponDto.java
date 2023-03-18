package com.dokong.board.repository.dto;

import com.dokong.board.domain.Coupon;
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

    @Builder
    public AddCouponDto(String couponName, int couponRate, String couponDetail, int minCouponPrice) {
        this.couponName = couponName;
        this.couponRate = couponRate;
        this.couponDetail = couponDetail;
        this.minCouponPrice = minCouponPrice;
    }

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(couponName)
                .couponRate(couponRate)
                .couponDetail(couponDetail)
                .minCouponPrice(minCouponPrice)
                .build();
    }
}
