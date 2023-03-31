package com.dokong.board.web.dto.orderdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.user.User;
import com.dokong.board.web.dto.orderproductdto.OrderProductRespDto;
import com.dokong.board.web.dto.product.SaveProductRespDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveOrderRespDto {

    private Long id;
    private Long userId;
    private String username;
    private Address address;
    private List<SaveProductRespDto> products;

    private List<OrderProductRespDto> orderProducts;


    public static SaveOrderRespDto of (Order order) {
        return SaveOrderRespDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .address(order.getAddress())
                .products(order.getOrderProducts().stream()
                        .map(o -> new SaveProductRespDto(o.getProduct().getItemName()))
                        .collect(Collectors.toList()))
                .orderProducts(order.getOrderProducts().stream()
                        .map(orderProduct -> new OrderProductRespDto(orderProduct))
                        .collect(Collectors.toList()))
                .build();
    }
}
