package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.boardlikedto.BoardLikeDto;
import com.dokong.board.web.dto.boardlikedto.BoardLikeRespDto;
import com.dokong.board.web.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class RestBoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/{id}/push")
    public ResponseEntity<?> pushBoardLike(@PathVariable("id") Long boardId, @Validated @RequestBody BoardLikeDto boardLikeDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        BoardLikeRespDto boardLikeRespDto = boardLikeService.pushBoardLike(boardId, boardLikeDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(boardLikeRespDto)
                .build();

        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @GetMapping("/{id}/count")
    public ResponseEntity<?> countBoardLike(@PathVariable("id") Long boardId) {
        long count = boardLikeService.countBoardLike(boardId);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(count)
                .build();

        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    private void bindingIllegalArgumentException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new IllegalArgumentException(errorMap.toString());
        }
    }
}
