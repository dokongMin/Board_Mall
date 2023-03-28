package com.dokong.board.web.handler;

import com.dokong.board.exception.NoExistUserException;
import com.dokong.board.web.controller.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.INVALID_PARAMETER.getHttpStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NoExistUserException.class)
    protected ResponseEntity<?> handleNoExitsUserException(NoExistUserException e) {
        ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.NO_EXIST_USER.getHttpStatus())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
