package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.savecartproductdto.DeleteCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductDto;
import com.dokong.board.web.dto.savecartproductdto.SaveCartProductRespDto;
import com.dokong.board.web.service.CartProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/items")
@Tag(name = "CartProduct", description = "CartProduct API Document")
public class RestCartProductController {

    private final CartProductService cartProductService;

    @Operation(summary = "장바구니 저장 API")
    @PostMapping("/add-cart")
    public ResponseEntity<?> saveCartProduct(@Validated @RequestBody SaveCartProductDto cartProductDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        SaveCartProductRespDto saveCartProductRespDto = cartProductService.saveCartProduct(cartProductDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(saveCartProductRespDto)
                .build();

        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "장바구니 삭제 API")
    @PostMapping("/delete-cart")
    public ResponseEntity<?> deleteCartProduct(@Validated @RequestBody DeleteCartProductDto deleteCartProductDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        DeleteCartProductDto deleteCartProductDtoEntity = cartProductService.deleteCartProduct(deleteCartProductDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.DELETE_REQUEST_SUCCESS.getMessage())
                .body(deleteCartProductDtoEntity)
                .build();
        return ResponseEntity.status(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus()).body(body);

    }

    @Operation(summary = "장바구니 전체 조회 API")
    @GetMapping("/cart/find-all/{id}")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long id) {
        List<SaveCartProductRespDto> allByUserId = cartProductService.findAllByUserId(id);
        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(allByUserId)
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
