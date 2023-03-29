package com.dokong.board.web.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    CREATE_REQUEST_SUCCESS(HttpStatus.CREATED, "Create Request Success"),
    UPDATE_REQUEST_SUCCESS(HttpStatus.CREATED, "Update Request Success"),
    DELETE_REQUEST_SUCCESS(HttpStatus.CREATED, "Delete Request Success"),
    REQUEST_SUCCESS(HttpStatus.OK, "Request Success");

    private final HttpStatus httpStatus;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
