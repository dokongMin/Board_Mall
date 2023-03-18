package com.dokong.board.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {
    BRONZE("브론즈"), SILVER("실버"), GOLD("골드");

    private String userRoleDescription;

    UserRole(String userRoleDescription) {
        this.userRoleDescription = userRoleDescription;
    }
}
