package com.dokong.board.domain.board;

public enum BoardStatus {

    CREATED("생성"), DELETED("삭제");
    private String description;

    BoardStatus(String description) {
        this.description = description;
    }
}
