package com.dokong.board.web.service;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.NoExistUserException;
import com.dokong.board.repository.CouponRepository;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.web.dto.coupondto.UpdateCouponDto;
import com.dokong.board.web.dto.coupondto.UpdateCouponRespDto;
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
    public AddCouponResponseDto addCouponByBoard(AddCouponDto couponDto) {
        User user = userService.findById(couponDto.getUserId());
        Coupon coupon = issueCoupon(couponDto);
        user.addCoupon(coupon);
        return AddCouponResponseDto.of(coupon);
    }

    @Transactional
    public AddCouponResponseDto addCoupon(AddCouponDto couponDto) {
        Long userId = couponDto.getUserId();
        User user = userService.findById(userId);
        Coupon coupon = issueCoupon(couponDto);
        user.addCoupon(coupon);
        return AddCouponResponseDto.of(coupon);
    }


    @Transactional
    public List<Coupon> bulkUpdateCoupon(UpdateCouponDto couponDto) {
        checkExistCoupon(couponDto.getCouponName());
        couponRepository.bulkUpdateCouponRate(couponDto.getCouponRate(), couponDto.getCouponName());
        return couponRepository.findByCouponName(couponDto.getCouponName());
    }


    public void checkExistCoupon(String couponName) {
        List<Coupon> couponList = couponRepository.findByCouponName(couponName);
        if (couponList.isEmpty()) {
            throw new IllegalArgumentException("해당 쿠폰은 존재하지 않습니다.");
        }
    }

    private Coupon issueCoupon(AddCouponDto addCouponDto) {
        validCouponIssue(addCouponDto);
        return couponRepository.save(addCouponDto.toEntity());
    }


    public void validCouponIssue(AddCouponDto couponDto) {
        if (couponDto.getCouponRate() > 99) {
            throw new IllegalArgumentException("할인율은 99보다 클 수 없습니다.");
        }
        if (couponDto.getMinCouponPrice() < 1000) {
            throw new IllegalArgumentException("최소 금액은 1000원 이상이어야 합니다.");
        }
    }

    public Coupon findById(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 쿠폰은 존재하지 않습니다.");
        });
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }
}
