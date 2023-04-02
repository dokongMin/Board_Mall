package com.dokong.board.domain.board;

import lombok.Getter;

@Getter
public enum BoardCommentStatus {

    CREATED("생성"), DELETED("삭제");
    private String description;

    BoardCommentStatus(String description) {
        this.description = description;
    }
}
