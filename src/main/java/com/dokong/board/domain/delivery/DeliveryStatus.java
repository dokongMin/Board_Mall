package com.dokong.board.domain.delivery;

import lombok.Getter;

@Getter
public enum DeliveryStatus {

    DELIVER_READY("배송 준비"),
    DELIVER_PROCEED("배송 진행"),
    DELIVER_COMPLETE("배송 완료");

    private String deliveryStatusDescription;

    DeliveryStatus(String deliveryStatusDescription) {
        this.deliveryStatusDescription = deliveryStatusDescription;
    }
}
