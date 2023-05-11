package com.dokong.board.web.dto.product;

import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.product.ProductStatus;
import lombok.*;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class FindProductDto {


    private String itemName;
    private int itemPrice;
    private int itemStock;
    private ProductStatus productStatus;
    private String categoryName;

    public static FindProductDto of(Product product) {
        return FindProductDto.builder()
                .itemName(product.getItemName())
                .itemPrice(product.getItemPrice())
                .itemStock(product.getItemStock())
                .productStatus(product.getProductStatus())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }
}
