package com.dokong.board.domain;

import com.dokong.board.domain.baseentity.BaseTimeEntity;
import com.dokong.board.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartProductId")
    private Long id;

    private String cartItemName;
    private int cartItemCount;
    private int cartItemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

//    public void createCartOrder(User user, Product product) {
////        if (cartProductCount > product.getItemStock()) {
////            throw new NotEnoughStockException("수량이 부족합니다.");
////        }
//        setUser(user);
////        setProduct(product);
//    }


    public void createCartOrder(User user) {
        this.user = user;
        user.getCartProducts().add(this);
    }

    @Builder
    public CartProduct(String cartItemName, int cartItemCount, int cartItemPrice) {
        this.cartItemName = cartItemName;
        this.cartItemCount = cartItemCount;
        this.cartItemPrice = cartItemPrice;
    }
}
