package com.dokong.board.web.dto.savecartproductdto;

import com.dokong.board.domain.CartProduct;
import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveCartProductRespDto {


    private Long userId;
    private String username;
    private Long productId;
    private String itemName;
    private Long cartId;
    private int cartItemCount;
    private int cartItemPrice;

    @Builder
    public SaveCartProductRespDto(Long userId, String username, Long productId, String itemName, Long cartId, int cartItemCount, int cartItemPrice) {
        this.userId = userId;
        this.username = username;
        this.productId = productId;
        this.itemName = itemName;
        this.cartId = cartId;
        this.cartItemCount = cartItemCount;
        this.cartItemPrice = cartItemPrice;
    }

    public static SaveCartProductRespDto of(CartProduct cartProduct, User user, Product product) {
        return SaveCartProductRespDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .productId(product.getId())
                .itemName(product.getItemName())
                .cartId(cartProduct.getId())
                .cartItemPrice(cartProduct.getCartItemPrice())
                .cartItemCount(cartProduct.getCartItemCount())
                .build();
    }
}
