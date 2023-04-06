package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.repository.board.BoardRepository;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.boarddto.*;
import com.dokong.board.web.dto.boardlikedto.BoardSearchCondition;
import com.dokong.board.web.dto.boardlikedto.SearchBoardDto;
import com.dokong.board.web.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Tag(name = "Board", description = "Board API Document")
public class RestBoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @Operation(summary = "게시글 작성 API")
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

    @Operation(summary = "게시글 수정 API")
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

    @Operation(summary = "게시글 삭제 API")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long boardId) {
        DeleteBoardRespDto deleteBoardDto = boardService.deleteBoard(boardId);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.DELETE_REQUEST_SUCCESS.getMessage())
                .body(deleteBoardDto)
                .build();
        return ResponseEntity.status(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "게시글 전체 조회 API")
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

    @Operation(summary = "게시글 상태 별 조회 API", description = "삭제된 게시글과 구분해서 조회가 가능")
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

    @Operation(summary = "게시글 검색 API")
    @GetMapping("/list-search")
    public List<SearchBoardDto> searchBoard(BoardSearchCondition condition) {
        return boardRepository.search(condition);
    }

    @Operation(summary = "게시글 페이징 API")
    @GetMapping("/list/page")
    public Page<SearchBoardDto> searchBoardPage(BoardSearchCondition condition, Pageable pageable) {
        return boardRepository.searchPage(condition, pageable);
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
