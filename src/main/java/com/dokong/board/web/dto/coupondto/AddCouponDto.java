package com.dokong.board.web.dto.coupondto;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.user.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCouponDto {

    private String couponName;
    private int couponRate;
    private String couponDetail;
    private int minCouponPrice;
    private CouponStatus couponStatus;

    private Long userId;

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(couponName)
                .couponRate(couponRate)
                .couponDetail(couponDetail)
                .minCouponPrice(minCouponPrice)
                .couponStatus(CouponStatus.UNUSED)
                .build();
    }
}
