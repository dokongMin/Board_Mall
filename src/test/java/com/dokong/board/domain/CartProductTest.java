package com.dokong.board.domain;

import com.dokong.board.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CartProductTest {
    
    @Test
    @DisplayName("장바구니_생성")
    public void createCartProduct () throws Exception{
        // given
        User user = User.builder()
                .username("alsghks")
                .password("12345")
                .name("정민환")
                .build();

        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .cartItemName(product.getItemName())
                .cartItemCount(10)
                .cartItemPrice(product.getItemPrice())
                .build();

        // when 
        cartProduct.createCartOrder(user);

        // then
        assertThat(user.getCartProducts().get(0).getCartItemName()).isEqualTo("사과");
     }

}