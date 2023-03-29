package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.product.Product;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.product.DeleteProductRespDto;
import com.dokong.board.web.dto.product.SaveProductDto;
import com.dokong.board.web.dto.product.UpdateProductDto;
import com.dokong.board.web.service.ProductService;
import lombok.RequiredArgsConstructor;
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
public class RestProductController {

    private final ProductService productService;
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

    @GetMapping("/item-list")
    public ResponseEntity<?> findAllProduct() {
        List<Product> allProduct = productService.findAll();
        List<SaveProductDto> collect = allProduct.stream()
                .map(p -> SaveProductDto.of(p))
                .collect(Collectors.toList());

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(collect)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        DeleteProductRespDto deleteProductRespDto = productService.deleteProduct(id);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(deleteProductRespDto)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
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
