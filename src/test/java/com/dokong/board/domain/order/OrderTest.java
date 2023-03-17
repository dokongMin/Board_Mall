package com.dokong.board.domain.order;

import com.dokong.board.domain.*;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {



    @Test
    @DisplayName("주문 생성")
    public void createOrder () throws Exception{
        // given

     }
//        Address address = new Address("서울", "110-332", "0000");
//
//        User user = User.builder()
//                .username("alsghks")
//                .password("1234")
//                .name("정민환")
//                .build();
//
//
//
//        Delivery delivery = Delivery.builder()
//                .address(address)
//                .deliveryStatus(DeliveryStatus.DELIVER_READY)
//                .build();

//        OrderProduct orderProduct = OrderProduct.builder()
//                .product(product)
//                .orderItemCount(10)
//                .orderItemPrice(product.getItemPrice())
//                .build();
//        List<OrderProduct> orderProducts = new ArrayList<>();
//        orderProducts.add(orderProduct);
//
//
//
//        Order order = Order.builder()
//                .orderStatus(OrderStatus.ORDER_COMPLETE)
//                .orderDate(LocalDateTime.now())
//                .address(address)
//                .user(user)
//                .orderProducts(orderProducts)
//                .delivery(delivery)
//                .build();
}