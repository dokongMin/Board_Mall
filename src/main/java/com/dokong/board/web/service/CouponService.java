package com.dokong.board.web.service;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.NoExistUserException;
import com.dokong.board.repository.CouponRepository;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.web.dto.coupondto.UpdateCouponDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserService userService;

    @Transactional
    public AddCouponResponseDto addCoupon(AddCouponDto couponDto, SessionUserDto userDto) {
        validCouponIssue(couponDto);
        User user = userService.findById(userDto.getId());
        user.addCoupon(couponDto.toEntity());
        return AddCouponResponseDto.of(couponRepository.save(couponDto.toEntity()));
    }

    @Transactional
    public int bulkUpdateCoupon(UpdateCouponDto couponDto) {
        checkExistCoupon(couponDto);
        int resultCount = couponRepository.bulkUpdateCouponRate(couponDto.getCouponRate(), couponDto.getCouponName());
        return resultCount;
    }


    public void checkExistCoupon(UpdateCouponDto couponDto) {
        List<Coupon> couponList = couponRepository.findByCouponName(couponDto.getCouponName());
        if (couponList.isEmpty()) {
            throw new IllegalArgumentException("해당 쿠폰은 존재하지 않습니다.");
        }
    }

    public void validCouponIssue(AddCouponDto couponDto) {
        if (couponDto.getCouponRate() > 99) {
            throw new IllegalArgumentException("할인율은 99보다 클 수 없습니다.");
        }
        if (couponDto.getMinCouponPrice() < 1000) {
            throw new IllegalArgumentException("최소 금액은 1000원 이상이어야 합니다.");
        }
    }

}
