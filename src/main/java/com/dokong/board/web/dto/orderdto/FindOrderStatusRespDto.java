package com.dokong.board.web.dto.orderdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import com.dokong.board.web.dto.orderproductdto.OrderProductRespDto;
import com.dokong.board.web.dto.orderproductdto.SaveOrderProductDto;
import com.dokong.board.web.dto.product.SaveProductRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindOrderStatusRespDto {

    private Long orderId;
    private Long userId;
    private String username;
    private Address address;
    private List<SaveProductRespDto> products;
    private List<OrderProductRespDto> orderProducts;
    private String orderStatus;

    public static FindOrderStatusRespDto of(Order order) {
        return FindOrderStatusRespDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .address(order.getAddress())
                .products(order.getOrderProducts().stream()
                        .map(o -> new SaveProductRespDto(o.getProduct().getItemName()))
                        .collect(Collectors.toList()))
                .orderProducts(order.getOrderProducts().stream()
                        .map(o -> new OrderProductRespDto(o))
                        .collect(Collectors.toList()))
                .orderStatus(order.getOrderStatus().getOrderStatusDescription())
                .build();
    }
}
