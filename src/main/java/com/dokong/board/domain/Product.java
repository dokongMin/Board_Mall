package com.dokong.board.domain;

import com.dokong.board.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    /**
     * 연관관계 편의 메소드
     */
    public void OrderProducts(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setProduct(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getProducts().add(this);
    }

    /**
     * 비즈니스 로직
     */
    public void addStock(int quantity) {
        this.itemStock += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.itemStock - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("수량이 부족합니다.");
        }
        itemStock = restStock;
    }

    /**
     * Builder
     */
    @Builder
    public Product(String itemName, int itemPrice, int itemStock, List<OrderProduct> orderProducts, Category category, List<CartProduct> cartProducts) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.orderProducts = orderProducts;
        this.category = category;
        this.cartProducts = cartProducts;
    }
}
