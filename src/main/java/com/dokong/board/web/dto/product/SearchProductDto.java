package com.dokong.board.web.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchProductDto {

    private String itemName;
    private int itemPrice;
    private int itemStock;

    @QueryProjection
    public SearchProductDto(String itemName, int itemPrice, int itemStock) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
    }
}
