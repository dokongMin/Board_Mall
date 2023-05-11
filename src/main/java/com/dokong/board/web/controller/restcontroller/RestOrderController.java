package com.dokong.board.web.controller.restcontroller;


import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.orderdto.FindOrderStatusDto;
import com.dokong.board.web.dto.orderdto.FindOrderStatusRespDto;
import com.dokong.board.web.dto.orderdto.SaveOrderDto;
import com.dokong.board.web.dto.orderdto.SaveOrderRespDto;
import com.dokong.board.web.service.OrderService;
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
@RequestMapping("/order")
@Tag(name = "Order", description = "Order API Document")
public class RestOrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성 API")
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

    @Operation(summary = "주문 취소 API")
    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {

        Long returnIds = orderService.cancelOrder(id);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.DELETE_REQUEST_SUCCESS.getMessage())
                .body(returnIds)
                .build();

        return ResponseEntity.status(SuccessCode.DELETE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "주문 전체 조회 API")
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllOrder() {
        List<SaveOrderRespDto> all = orderService.findAll();

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(all)
                .build();

        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "주문 상태 별 조회 API", description = "주문 상태에 따라 조회")
    @GetMapping("/find-all/order-status")
    public ResponseEntity<?> findAllByOrderStatus(@RequestBody FindOrderStatusDto findOrderStatusDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        List<FindOrderStatusRespDto> allByOrderStatus = orderService.findAllByOrderStatus(findOrderStatusDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(allByOrderStatus)
                .build();

        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @Operation(summary = "주문 유저 별 조회 API")
    @GetMapping("/find-all/{id}")
    public ResponseEntity<?> findAllByUserId(@PathVariable("id") Long userId) {
        List<FindOrderStatusRespDto> allByUserId = orderService.findAllByUserId(userId);

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
