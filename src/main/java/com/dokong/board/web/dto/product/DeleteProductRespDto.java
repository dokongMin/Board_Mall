package com.dokong.board.web.dto.product;

import com.dokong.board.domain.product.Product;
import com.dokong.board.domain.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProductRespDto {

    private Long id;
    private String productStatus;

    public static DeleteProductRespDto of(Product product) {
        return DeleteProductRespDto.builder()
                .id(product.getId())
                .productStatus(product.getProductStatus().getDescription())
                .build();
    }
}
