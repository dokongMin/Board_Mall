package com.dokong.board.web.dto.orderdto;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.user.User;
import com.dokong.board.web.dto.orderproductdto.OrderProductRespDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SaveOrderRespDto {

    private Long userId;
    private String username;
    private Address address;
    private List<String> products;

    private List<OrderProductRespDto> orderProducts;


    @Builder
    public SaveOrderRespDto(Long userId, String username, Address address, List<String> products, List<OrderProductRespDto> orderProducts) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.orderProducts = orderProducts;
    }

    public static SaveOrderRespDto of (Order order, User user, Address address, List<Product> products, List<OrderProduct> orderProducts) {
        return SaveOrderRespDto.builder()
                .userId(user.getId())
                .address(address)
                .products(products.stream()
                        .map(p -> p.getItemName()).collect(Collectors.toList()))
                .orderProducts(order.getOrderProducts().stream()
                        .map(orderItem -> new OrderProductRespDto(orderItem))
                        .collect(Collectors.toList()))
                .build();
    }
}
