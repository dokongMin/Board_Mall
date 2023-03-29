package com.dokong.board.web.controller.restcontroller;


import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.web.dto.coupondto.UpdateCouponDto;
import com.dokong.board.web.dto.coupondto.UpdateCouponRespDto;
import com.dokong.board.web.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
@RequestMapping("/admin")
public class RestCouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/add")
    public ResponseEntity<?> saveCoupon(@Validated @RequestBody AddCouponDto addCouponDto, BindingResult bindingResult) {

        bindingIllegalArgumentException(bindingResult);
        AddCouponResponseDto addCouponResponseDto = couponService.addCoupon(addCouponDto);

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(addCouponResponseDto)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(body);
    }

    @PostMapping("/coupon/update")
    public ResponseEntity<?> updateCoupon(@Validated @RequestBody UpdateCouponDto updateCouponDto, BindingResult bindingResult) {
        bindingIllegalArgumentException(bindingResult);
        List<Coupon> coupons = couponService.bulkUpdateCoupon(updateCouponDto);

        List<UpdateCouponRespDto> collect = coupons.stream()
                .map(c -> UpdateCouponRespDto.of(c))
                .collect(Collectors.toList());
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(collect);
    }

    @GetMapping("/coupon/coupon-list")
    public ResponseEntity<?> findAllCoupon() {
        List<Coupon> allCoupon = couponService.findAll();

        List<AddCouponResponseDto> collect = allCoupon.stream()
                .map(c -> AddCouponResponseDto.of(c))
                .collect(Collectors.toList());

        CommonResponseDto<Object> body = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(collect)
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
