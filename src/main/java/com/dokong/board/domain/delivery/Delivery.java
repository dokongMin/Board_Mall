package com.dokong.board.domain.delivery;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.order.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private deliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }
}
