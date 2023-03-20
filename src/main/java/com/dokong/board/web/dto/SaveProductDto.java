package com.dokong.board.web.dto;

import com.dokong.board.domain.Category;
import com.dokong.board.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveProductDto {

    private String itemName;
    private int itemPrice;
    private int itemStock;
    private Category category;

    @Builder
    public SaveProductDto(String itemName, int itemPrice, int itemStock, Category category) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.category = category;
    }

    public Product toEntity() {
        return Product.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemStock(itemStock)
                .category(category)
                .build();
    }

    public static SaveProductDto of(Product product) {
        return SaveProductDto.builder()
                .itemName(product.getItemName())
                .itemPrice(product.getItemPrice())
                .itemStock(product.getItemStock())
                .category(product.getCategory())
                .build();
    }
}
