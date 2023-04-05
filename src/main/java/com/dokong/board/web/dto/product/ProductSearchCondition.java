package com.dokong.board.web.dto.product;

import lombok.Data;

@Data
public class ProductSearchCondition {

    private String itemName;
    private int priceGoe;
    private int priceLoe;
}
