package com.dokong.board.web.dto.eventcoupon;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCoupon {

    private String couponName;
    private int couponCount;

    private static final int END = 0;

    public void decreaseCount() {
        this.couponCount--;
    }

    public boolean endCount() {
        return END == this.couponCount;
    }

}
