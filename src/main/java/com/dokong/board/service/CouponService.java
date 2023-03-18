package com.dokong.board.service;

import com.dokong.board.domain.Coupon;
import com.dokong.board.repository.CouponRepository;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.repository.dto.AddCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

//    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public Long addCoupon(AddCouponDto couponDto) {
        validCouponIssue(couponDto);
        Coupon coupon = couponRepository.save(couponDto.toEntity());
        return coupon.getId();
    }

    private void validCouponIssue(AddCouponDto couponDto) {
        if (couponDto.getCouponRate() > 99) {
            throw new IllegalArgumentException("할인율은 99보다 클 수 없습니다.");
        }
        if (couponDto.getMinCouponPrice() < 1000) {
            throw new IllegalArgumentException("최소 금액은 1000원 이상이어야 합니다.");
        }
    }

//    @Transactional
//    public void addCoupon(String username) {
//
//    }
}
