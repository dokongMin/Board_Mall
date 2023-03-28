package com.dokong.board.web.controller;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponseDto<T>{

    private HttpStatus code;
    private String msg;
    private T body;

    @Builder
    public CommonResponseDto(HttpStatus code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
