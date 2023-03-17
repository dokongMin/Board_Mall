package com.dokong.board.domain.order;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum OrderStatus {
    ORDER_COMPLETE("주문 완료"), ORDER_CANCEL("주문 취소"), PAY_COMPLETE("결제 완료");

    private String orderStatusDescription;

    OrderStatus(String orderStatusDescription) {
        this.orderStatusDescription = orderStatusDescription;
    }
}
