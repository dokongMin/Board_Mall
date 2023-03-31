package com.dokong.board.web.dto.product;

import com.dokong.board.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveProductRespDto {

    private String itemName;

    public SaveProductRespDto(OrderProduct orderProduct) {
        this.itemName = orderProduct.getProduct().getItemName();
    }
}
