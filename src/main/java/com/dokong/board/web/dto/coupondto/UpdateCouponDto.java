package com.dokong.board.web.dto.coupondto;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.coupon.CouponStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCouponDto {

    private Long id;
    private String couponName;
    private int couponRate;

    @Builder
    public UpdateCouponDto(Long id, String couponName, int couponRate) {
        this.id = id;
        this.couponName = couponName;
        this.couponRate = couponRate;
    }

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(couponName)
                .couponRate(couponRate)
                .build();
    }
}
