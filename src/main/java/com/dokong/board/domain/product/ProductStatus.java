package com.dokong.board.domain.product;

import lombok.Getter;

@Getter
public enum ProductStatus {

    OPEN("공개"), DELETE("삭제");

    private String description;

    ProductStatus(String description) {
        this.description = description;
    }
}
