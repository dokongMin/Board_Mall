package com.dokong.board.web.dto.savecartproductdto;

import com.dokong.board.domain.CartProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class SaveCartProductDto {

    private Long id;
    private int cartItemCount;
    private int cartItemPrice;

    @Builder
    public SaveCartProductDto(Long id, int cartItemCount, int cartItemPrice) {
        this.id = id;
        this.cartItemCount = cartItemCount;
        this.cartItemPrice = cartItemPrice;
    }

    public CartProduct toEntity() {
        return CartProduct.builder()
                .cartItemCount(cartItemCount)
                .cartItemPrice(cartItemPrice)
                .build();
    }

    public static SaveCartProductDto of(CartProduct cartProduct) {
        return SaveCartProductDto.builder()
                .cartItemCount(cartProduct.getCartItemCount())
                .cartItemPrice(cartProduct.getCartItemPrice())
                .build();
    }

}
