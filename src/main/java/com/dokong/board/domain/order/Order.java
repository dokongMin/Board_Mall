package com.dokong.board.domain.order;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.OrderProduct;
import com.dokong.board.domain.baseentity.BaseTimeEntity;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.delivery.Delivery;
import com.dokong.board.domain.delivery.DeliveryStatus;
import com.dokong.board.exception.AlreadyDeliverException;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    /**
     * 연관관계 편의 메소드
     */
    private void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    private void setOrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 비즈니스 로직
     */

    public Order createOrder(User user, Delivery delivery, List<OrderProduct> orderProducts) {
        setUser(user);
        setDelivery(delivery);
        for (OrderProduct orderProduct : orderProducts) {
            setOrderProducts(orderProduct);
        }
        return this;
    }
    public void cancelOrder() {
        if (checkDeliveryStatus()) {
            throw new AlreadyDeliverException("배송 중이거나 배송 완료 된 제품은 주문 취소가 불가합니다.");
        }
        this.orderStatus = OrderStatus.ORDER_CANCEL;
        for (OrderProduct orderProduct : this.orderProducts) {
            orderProduct.cancel();
        }
    }

    private boolean checkDeliveryStatus() {
        return delivery.getDeliveryStatus() == DeliveryStatus.DELIVER_PROCEED || delivery.getDeliveryStatus() == DeliveryStatus.DELIVER_COMPLETE;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Builder
     */
    @Builder
    public Order(OrderStatus orderStatus, Address address) {
        this.orderStatus = orderStatus;
        this.address = address;
    }

}
