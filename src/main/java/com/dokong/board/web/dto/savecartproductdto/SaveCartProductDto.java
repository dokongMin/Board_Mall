package com.dokong.board.web.dto.savecartproductdto;

import com.dokong.board.domain.CartProduct;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveCartProductDto {

    private Long id;
    private int cartItemCount;
    private int cartItemPrice;
    private Long userId;
    private Long productId;


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
