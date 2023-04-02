package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.boarddto.*;
import com.dokong.board.web.service.BoardService;
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
@RequestMapping("/board")
public class RestBoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<?> saveBoard(@Validated @RequestBody SaveBoardReqDto saveBoardReqDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);

        SaveBoardRespDto saveBoardRespDto = boardService.saveBoard(saveBoardReqDto);
        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(saveBoardRespDto)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable("id") Long boardId, @Validated @RequestBody UpdateBoardDto updateBoardDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        UpdateBoardDto updateBoardDtoEntity = boardService.updateBoard(boardId, updateBoardDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(updateBoardDtoEntity)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long boardId) {
        DeleteBoardRespDto deleteBoardDto = boardService.deleteBoard(boardId);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.DELETE_REQUEST_SUCCESS.getMessage())
                .body(deleteBoardDto)
                .build();
        return ResponseEntity.status(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @GetMapping("/list/all")
    public ResponseEntity<?> findAll() {
        List<FindBoardDto> all = boardService.findAll();

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(all)
                .build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAllByBoardStatus(@RequestBody FindBoardDto findBoardDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);

        List<FindBoardDto> allByBoardStatus = boardService.findAllByBoardStatus(findBoardDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(allByBoardStatus)
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
