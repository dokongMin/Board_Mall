package com.dokong.board.web.dto;

import com.dokong.board.domain.OrderProduct;
import com.dokong.board.web.dto.product.SaveProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveOrderProductDto {

    private Long id;
    private int orderItemPrice;
    private int orderItemCount;

    @Builder
    public SaveOrderProductDto(Long id, int orderItemPrice, int orderItemCount) {
        this.id = id;
        this.orderItemPrice = orderItemPrice;
        this.orderItemCount = orderItemCount;
    }

    public OrderProduct toEntity(){
        return OrderProduct.builder()
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
