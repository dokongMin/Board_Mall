package com.dokong.board.web.dto.eventcoupon;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCoupon {


    private String couponName;
    private int couponRate;
    private String couponDetail;
    private int minCouponPrice;
    private CouponStatus couponStatus;
    private int limit;

    private static final int END = 0;

    public void decreaseCount() {
        this.limit--;
    }

    public boolean endCount() {
        return END == this.limit;
    }

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(couponName)
                .couponRate(couponRate)
                .couponDetail(couponDetail)
                .minCouponPrice(minCouponPrice)
                .couponStatus(CouponStatus.UNUSED)
                .build();
    }

    public static EventCoupon of(Coupon coupon) {
        return EventCoupon.builder()
                .couponName(coupon.getCouponName())
                .couponDetail(coupon.getCouponDetail())
                .couponRate(coupon.getCouponRate())
                .couponDetail(coupon.getCouponDetail())
                .minCouponPrice(coupon.getMinCouponPrice())
                .build();
    }


}
