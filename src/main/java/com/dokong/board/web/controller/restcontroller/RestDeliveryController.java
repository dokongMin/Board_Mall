package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.deliverydto.SaveDeliveryDto;
import com.dokong.board.web.dto.orderdto.SaveOrderRespDto;
import com.dokong.board.web.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class RestDeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("add")
    public ResponseEntity<?> saveDelivery(@RequestBody SaveDeliveryDto saveDeliveryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new IllegalArgumentException(errorMap.toString());
        }
        SaveDeliveryDto saveDeliveryDtoEntity = deliveryService.saveDelivery(saveDeliveryDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(saveDeliveryDtoEntity)
                .build();

        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }
}
