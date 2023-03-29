package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.Category;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.categorydto.CategoryDto;
import com.dokong.board.web.service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class RestCategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> saveCategory(@Validated @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        CategoryDto saveCategory = categoryService.saveCategory(categoryDto);
        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(saveCategory)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @GetMapping("/category-list")
    public ResponseEntity<?> findAllCategory() {
        List<Category> all = categoryService.findAll();

        List<CategoryDto> collect = all.stream()
                .map(c -> CategoryDto.of(c))
                .collect(Collectors.toList());

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(collect)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
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
