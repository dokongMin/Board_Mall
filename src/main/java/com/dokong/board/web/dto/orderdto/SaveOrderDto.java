package com.dokong.board.web.dto.orderdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveOrderDto {

    private Long id;
    private OrderStatus orderStatus;
    private Address address;

    @Builder
    public SaveOrderDto(Long id, OrderStatus orderStatus, Address address) {
        this.id = id;
        this.orderStatus = OrderStatus.ORDER_COMPLETE;
        this.address = address;
    }

    public Order toEntity(){
        return Order.builder()
                .orderStatus(orderStatus)
                .address(address)
                .build();
    }

    public static SaveOrderDto of(Order order) {
        return SaveOrderDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .address(order.getAddress())
                .build();
    }
}
