package com.dokong.board.web.dto.orderdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveOrderDto {

    private Long id;
    private OrderStatus orderStatus;
    private Address address;
    private Long userId;
    private SaveDeliveryDto saveDeliveryDto;
    private List<SaveOrderProductDto> saveOrderProductDtos;

    public Order toEntity(){
        return Order.builder()
                .orderStatus(OrderStatus.ORDER_COMPLETE)
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
