package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.product.Product;
import com.dokong.board.repository.product.ProductRepository;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.product.*;
import com.dokong.board.web.service.ProductService;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Tag(name = "Product", description = "Product API Document")
public class RestProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @Operation(summary = "상품 저장 API")
    @PostMapping("/add")
    public ResponseEntity<?> saveProduct(@Validated @RequestBody SaveProductDto saveProductDto, BindingResult bindingResult) {
        getBindingResult(bindingResult);
        SaveProductDto saveProduct = productService.saveProduct(saveProductDto);
        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(saveProduct)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "상품 수정 API")
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto updateProductDto, BindingResult bindingResult) {
        getBindingResult(bindingResult);
        UpdateProductDto updateProduct = productService.updateProduct(id, updateProductDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(updateProduct)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "상품 전체 조회 API")
    @GetMapping("/item-list")
    public ResponseEntity<?> findAllProduct() {
        List<FindProductDto> allProduct = productService.findAll();

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(allProduct)
                .build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "상품 삭제 API")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        DeleteProductRespDto deleteProductRespDto = productService.deleteProduct(id);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(deleteProductRespDto)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "상품 검색 API")
    @GetMapping("/item-list/search")
    public List<SearchProductDto> search(ProductSearchCondition condition) {
        return productRepository.search(condition);
    }

    @Operation(summary = "상품 페이징 API")
    @GetMapping("/item-list/search/page")
    public Page<SearchProductDto> searchPage(ProductSearchCondition condition, Pageable pageable) {
        return productRepository.searchPage(condition, pageable);
    }
    private void getBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new IllegalArgumentException(errorMap.toString());
        }
    }
}
