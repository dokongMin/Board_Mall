package com.dokong.board.web.dto.product;

import com.dokong.board.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductDto {

    private String itemName;
    private int itemPrice;
    private int itemStock;

    @Builder
    public UpdateProductDto(String itemName, int itemPrice, int itemStock) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
    }

    public Product toEntity(UpdateProductDto productDto) {
        return Product.builder()
                .itemName(productDto.getItemName())
                .itemPrice(productDto.getItemPrice())
                .itemStock(productDto.getItemStock())
                .build();
    }

    public static UpdateProductDto of(Product product) {
        return UpdateProductDto.builder()
                .itemName(product.getItemName())
                .itemPrice(product.getItemPrice())
                .itemStock(product.getItemStock())
                .build();
    }
}
