package com.dokong.board.domain;

import com.dokong.board.domain.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderProductId")
    private Long id;

    private int orderItemPrice;
    private int orderItemCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;


    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.getOrderProducts().add(this);
    }


    /**
     * 비즈니스 로직
     */
    public void order(Product product) {
        setProduct(product);
        product.removeStock(orderItemCount);
    }

    public void cancel() {
        product.addStock(orderItemCount);
        product.getOrderProducts().remove(this);
    }

    /**
     * Builder
     */
    @Builder
    public OrderProduct(Long id, int orderItemPrice, int orderItemCount) {
        this.id = id;
        this.orderItemPrice = orderItemPrice;
        this.orderItemCount = orderItemCount;
    }
}
