package com.dokong.board.repository;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.web.dto.coupondto.UpdateCouponDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("벌크_쿠폰_수정")
    @Transactional
    public void bulkUpdateCoupon () throws Exception{
        // given
        Coupon coupon1 = getCoupon();

        Coupon coupon2 = getCoupon();

        Coupon coupon3 = getCoupon();

        couponRepository.save(coupon1);
        couponRepository.save(coupon2);
        couponRepository.save(coupon3);

        UpdateCouponDto couponDto = UpdateCouponDto.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponRate(20)
                .build();
        // when
        couponRepository.bulkUpdateCouponRate(couponDto.getCouponRate(), couponDto.getCouponName());
        // then
        assertThat(couponRepository.findByCouponName("회원가입 축하 쿠폰").get(0).getCouponRate()).isEqualTo(20);
        assertThat(couponRepository.findByCouponName("회원가입 축하 쿠폰").get(1).getCouponRate()).isEqualTo(20);
        assertThat(couponRepository.findByCouponName("회원가입 축하 쿠폰").get(2).getCouponRate()).isEqualTo(20);

    }

    private Coupon getCoupon() {
        return Coupon.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponDetail("회원가입을 축하하여 드리는 쿠폰입니다.")
                .couponRate(15)
                .minCouponPrice(10000)
                .build();
    }
}