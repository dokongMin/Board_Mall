package com.dokong.board.domain;

import com.dokong.board.exception.NotEnoughStockException;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    public void removeStock() throws Exception {
        // given
        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();

        // when
        product.removeStock(10);
        // then
        assertThat(product.getItemStock()).isEqualTo(0);
    }

    @Test
    public void removeStockException() throws Exception {
        // given
        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();

        // then
        assertThrows(NotEnoughStockException.class, () -> {
            product.removeStock(11);
        });
    }

    @Test
    public void addStock () throws Exception{
        // given
        Product product = Product.builder()
                .itemName("사과")
                .itemStock(10)
                .itemPrice(2000)
                .build();
        // when
        product.addStock(10);
        // then
        assertThat(product.getItemStock()).isEqualTo(20);

     }
}