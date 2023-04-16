package com.dokong.board.web.dto.eventcoupon;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCoupon {

    private String couponName;
    private int limit;

    private static final int END = 0;

    public void decreaseCount() {
        this.limit--;
    }

    public boolean endCount() {
        return END == this.limit;
    }

}
