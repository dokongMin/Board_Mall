package com.dokong.board.web.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    CREATE_REQUEST_SUCCESS(HttpStatus.CREATED, "Create request success"),
    REQUEST_SUCCESS(HttpStatus.OK, "Request success");

    private final HttpStatus httpStatus;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
