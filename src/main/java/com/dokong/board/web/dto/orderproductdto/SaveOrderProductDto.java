package com.dokong.board.web.dto.orderproductdto;

import com.dokong.board.domain.OrderProduct;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveOrderProductDto {

    private Long id;
    private int orderItemPrice;
    private int orderItemCount;

    private Long userId;
    private Long productId;
    private Long couponId;

    public OrderProduct toEntity(){
        return OrderProduct.builder()
                .id(id)
                .orderItemPrice(orderItemPrice)
                .orderItemCount(orderItemCount)
                .build();
    }

    public static SaveOrderProductDto of(OrderProduct orderProduct) {
        return SaveOrderProductDto.builder()
                .id(orderProduct.getId())
                .orderItemPrice(orderProduct.getOrderItemPrice())
                .orderItemCount(orderProduct.getOrderItemCount())
                .build();
    }
}
