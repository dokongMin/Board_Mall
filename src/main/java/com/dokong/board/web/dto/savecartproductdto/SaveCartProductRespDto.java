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

    private Long cartId;
    private Long productId;
    private String itemName;
    private int cartItemCount;
    private int cartItemPrice;

    private Long userId;
    private String username;




    @Builder
    public SaveCartProductRespDto(Long userId, String username, Long productId, String itemName, Long cartId, int cartItemCount, int cartItemPrice) {
        this.cartId = cartId;
        this.productId = productId;
        this.itemName = itemName;
        this.cartItemCount = cartItemCount;
        this.cartItemPrice = cartItemPrice;
        this.userId = userId;
        this.username = username;
    }

    public static SaveCartProductRespDto of(CartProduct cartProduct, User user, Product product) {
        return SaveCartProductRespDto.builder()
                .cartId(cartProduct.getId())
                .productId(product.getId())
                .itemName(product.getItemName())
                .cartItemPrice(cartProduct.getCartItemPrice())
                .cartItemCount(cartProduct.getCartItemCount())
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }
}
