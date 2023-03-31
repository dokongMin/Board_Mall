package com.dokong.board.web.controller.restcontroller;


import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderdto.SaveOrderRespDto;
import com.dokong.board.web.service.OrderService;
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
@RequestMapping("/order")
public class RestOrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody SaveOrderDto saveOrderDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);

        SaveOrderRespDto saveOrderRespDto = orderService.saveOrder(saveOrderDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(saveOrderRespDto)
                .build();

        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {

        orderService.cancelOrder(id);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.DELETE_REQUEST_SUCCESS.getMessage())
                .body(id)
                .build();

        return ResponseEntity.status(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    /**
     * 주문완료 상품만 보여주는 쿼리
     */

    /**
     * 주문 취소 상품만 보여주는 쿼리
     */


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
