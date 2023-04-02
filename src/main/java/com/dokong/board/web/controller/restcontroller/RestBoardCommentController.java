package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.boardcommentdto.BoardCommentDto;
import com.dokong.board.web.dto.boardcommentdto.FindBoardCommentRespDto;
import com.dokong.board.web.dto.boardcommentdto.UpdateBoardCommentDto;
import com.dokong.board.web.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/comment")
public class RestBoardCommentController {

    private final BoardCommentService boardCommentService;

    @PostMapping("/write/{id}")
    public ResponseEntity<?> writeBoardComment(@PathVariable("id") Long boardId, @Validated @RequestBody BoardCommentDto boardCommentDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);

        BoardCommentDto boardCommentDtoEntity = boardCommentService.saveBoardComment(boardCommentDto, boardId);
        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(boardCommentDtoEntity)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<?> updateBoardComment(@PathVariable("id") Long boardCommentId, @Validated @RequestBody UpdateBoardCommentDto boardCommentDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        BoardCommentDto boardCommentDtoEntity = boardCommentService.updateBoardComment(boardCommentId, boardCommentDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(boardCommentDtoEntity)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByBoardId(@PathVariable("id") Long boardId) {
        List<FindBoardCommentRespDto> byBoardId = boardCommentService.findByBoardId(boardId);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(byBoardId)
                .build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long boardCommentId) {
        FindBoardCommentRespDto findBoardCommentRespDto = boardCommentService.deleteBoard(boardCommentId);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.DELETE_REQUEST_SUCCESS.getMessage())
                .body(findBoardCommentRespDto)
                .build();
        return ResponseEntity.status(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus()).body(body);
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
