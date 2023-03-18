package com.dokong.board.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {
    USED("사용 완료"), UNUSED("사용 전");

    private String CouponDescription;

    CouponStatus(String couponDescription) {
        CouponDescription = couponDescription;
    }
}
