package com.dokong.board.web.dto.coupondto;

import com.dokong.board.domain.coupon.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCouponRespDto {

    private Long id;
    private String couponName;
    private int couponRate;

    public static UpdateCouponRespDto of(Coupon coupon) {
        return UpdateCouponRespDto.builder()
                .id(coupon.getId())
                .couponName(coupon.getCouponName())
                .couponRate(coupon.getCouponRate())
                .build();
    }
}
