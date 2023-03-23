package com.dokong.board.domain.delivery;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.order.Order;
import com.dokong.board.domain.order.OrderStatus;
import com.dokong.board.exception.NotPaidException;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        if (order.getOrderStatus() == OrderStatus.ORDER_CANCEL || order.getOrderStatus() == OrderStatus.ORDER_COMPLETE) {
            throw new NotPaidException("결제가 완료되지 않은 주문은 배송이 불가합니다.");
        }
        this.deliveryStatus = deliveryStatus;
    }

    @Builder
    public Delivery(Address address, DeliveryStatus deliveryStatus) {
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }
}
