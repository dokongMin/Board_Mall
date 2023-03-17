package com.dokong.board.domain.order;

import com.dokong.board.domain.*;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import com.dokong.board.exception.AlreadyDeliverException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {


    @Test
    @DisplayName("주문_생성")
    public void createOrder() throws Exception {
        // given
        Order order = createOrderDummy();
        // then
        assertThat(order.getOrderProducts().get(0).getOrderItemPrice()).isEqualTo(2000);
        assertThat(order.getOrderProducts().get(0).getProduct().getItemStock()).isEqualTo(5);
    }


    @Test
    @DisplayName("주문_취소")
    public void cancelOrder () throws Exception{
        // given
        Order order = createOrderDummy();
        // when 
        order.cancelOrder();
        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCEL);
        assertThat(order.getOrderProducts().get(0).getProduct().getItemStock()).isEqualTo(10);
        
     }


     @Test
     @DisplayName("주문_취소_예외")
     public void cancelOrderException () throws Exception{
         // given
         Order orderComplete = createOrderDummy_Delivery_Complete();
         Order orderProceed = createOrderDummy_Delivery_Proceed();

         // then
         assertThrows(AlreadyDeliverException.class, () -> {
             orderComplete.cancelOrder();
         });
         assertThrows(AlreadyDeliverException.class, () -> {
             orderProceed.cancelOrder();
         });
      }
    private Order createOrderDummy() {
        Address address = new Address("서울", "110-332", "0000");

        User user = User.builder()
                .username("alsghks")
                .password("1234")
                .name("정민환")
                .build();

        Delivery delivery = Delivery.builder()
                .address(address)
                .deliveryStatus(DeliveryStatus.DELIVER_READY)
                .build();

        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .orderItemCount(5)
                .orderItemPrice(product.getItemPrice())
                .build();
        orderProduct.order();

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER_COMPLETE)
                .orderDate(LocalDateTime.now())
                .address(address)
                .build();

        order.createOrder(user, delivery, orderProduct);
        return order;
    }

    private Order createOrderDummy_Delivery_Complete() {
        Address address = new Address("서울", "110-332", "0000");

        User user = User.builder()
                .username("alsghks")
                .password("1234")
                .name("정민환")
                .build();

        Delivery delivery = Delivery.builder()
                .address(address)
                .deliveryStatus(DeliveryStatus.DELIVER_COMPLETE)
                .build();

        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .orderItemCount(5)
                .orderItemPrice(product.getItemPrice())
                .build();
        orderProduct.order();

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER_COMPLETE)
                .orderDate(LocalDateTime.now())
                .address(address)
                .build();

        order.createOrder(user, delivery, orderProduct);
        return order;
    }
    private Order createOrderDummy_Delivery_Proceed() {
        Address address = new Address("서울", "110-332", "0000");

        User user = User.builder()
                .username("alsghks")
                .password("1234")
                .name("정민환")
                .build();

        Delivery delivery = Delivery.builder()
                .address(address)
                .deliveryStatus(DeliveryStatus.DELIVER_PROCEED)
                .build();

        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .orderItemCount(5)
                .orderItemPrice(product.getItemPrice())
                .build();
        orderProduct.order();

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER_COMPLETE)
                .orderDate(LocalDateTime.now())
                .address(address)
                .build();

        order.createOrder(user, delivery, orderProduct);
        return order;
    }
}