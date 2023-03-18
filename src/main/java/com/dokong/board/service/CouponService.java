package com.dokong.board.service;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.repository.CouponRepository;
import com.dokong.board.repository.dto.coupondto.AddCouponDto;
import com.dokong.board.repository.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.repository.dto.coupondto.UpdateCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public AddCouponResponseDto addCoupon(AddCouponDto couponDto) {
        validCouponIssue(couponDto);
        return AddCouponResponseDto.of(couponRepository.save(couponDto.toEntity()));
    }

    @Transactional
    public int bulkUpdateCoupon(UpdateCouponDto couponDto) {
        checkExistCoupon(couponDto);
        int resultCount = couponRepository.bulkUpdateCouponRate(couponDto.getCouponRate(), couponDto.getCouponName());
        return resultCount;
    }


    private void checkExistCoupon(UpdateCouponDto couponDto) {
        List<Coupon> couponList = couponRepository.findByCouponName(couponDto.getCouponName());
        if (couponList.isEmpty()) {
            throw new IllegalArgumentException("해당 쿠폰은 존재하지 않습니다.");
        }
    }

    private void validCouponIssue(AddCouponDto couponDto) {
        if (couponDto.getCouponRate() > 99) {
            throw new IllegalArgumentException("할인율은 99보다 클 수 없습니다.");
        }
        if (couponDto.getMinCouponPrice() < 1000) {
            throw new IllegalArgumentException("최소 금액은 1000원 이상이어야 합니다.");
        }
    }

}
