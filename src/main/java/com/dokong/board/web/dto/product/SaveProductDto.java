package com.dokong.board.web.dto.product;

import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.product.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SaveProductDto {

    private Long id;
    @Length(min = 1, max = 20)
    @NotBlank
    private String itemName;
    @Max(99999999)
    @Min(1000)
    @NotNull
    private int itemPrice;
    @Max(99999999)
    @Min(5)
    @NotNull
    private int itemStock;

    private ProductStatus productStatus;

    @NotBlank
    private String categoryName;

    @Builder
    public SaveProductDto(Long id, String itemName, int itemPrice, int itemStock, ProductStatus productStatus, String categoryName) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.productStatus = productStatus;
        this.categoryName = categoryName;
    }

    public Product toEntity() {
        return Product.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemStock(itemStock)
                .productStatus(ProductStatus.OPEN)
                .build();
    }

    public static SaveProductDto of(Product product) {
        return SaveProductDto.builder()
                .id(product.getId())
                .itemName(product.getItemName())
                .itemPrice(product.getItemPrice())
                .itemStock(product.getItemStock())
                .categoryName(product.getCategory().getCategoryName())
                .productStatus(product.getProductStatus())
                .build();
    }
}
