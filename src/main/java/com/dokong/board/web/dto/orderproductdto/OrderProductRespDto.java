package com.dokong.board.web.dto.orderproductdto;

import com.dokong.board.domain.OrderProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductRespDto {
    private int itemPrice;
    private int itemCount;

    @Builder
    public OrderProductRespDto(int itemPrice, int itemCount) {
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
    }

    public OrderProductRespDto(OrderProduct orderProduct) {
        itemPrice = orderProduct.getOrderItemPrice();
        itemCount = orderProduct.getOrderItemCount();
    }
}
