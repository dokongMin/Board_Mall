package com.dokong.board.web.dto.product;

import com.dokong.board.domain.Category;
import com.dokong.board.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SaveProductDto {

    private Long id;
    @Length(min = 1, max = 20)
    @NotBlank
    private String itemName;
    @Min(1000)
    @NotBlank
    private int itemPrice;
    @Min(5)
    @NotBlank
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
