package com.dokong.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long id;

    private String itemName;
    private int itemPrice;
    private int itemStock;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProducts = new ArrayList<>();

    public void OrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setProduct(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getProducts().add(this);
    }
}
