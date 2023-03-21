package com.dokong.board.web.dto.product;

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

    private Long id;
    private String itemName;
    private int itemPrice;
    private int itemStock;

    @Builder
    public SaveProductDto(Long id, String itemName, int itemPrice, int itemStock) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
    }

    public Product toEntity() {
        return Product.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemStock(itemStock)
                .build();
    }

    public static SaveProductDto of(Product product) {
        return SaveProductDto.builder()
                .id(product.getId())
                .itemName(product.getItemName())
                .itemPrice(product.getItemPrice())
                .itemStock(product.getItemStock())
                .build();
    }
}
