package com.dokong.board.web.dto.orderproductdto;

import com.dokong.board.domain.OrderProduct;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductRespDto {
    private int itemPrice;
    private int itemCount;


    public OrderProductRespDto(OrderProduct orderProduct) {
        itemPrice = orderProduct.getOrderItemPrice();
        itemCount = orderProduct.getOrderItemCount();
    }
}
