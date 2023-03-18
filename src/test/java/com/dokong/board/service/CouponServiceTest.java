package com.dokong.board.service;

import com.dokong.board.repository.dto.coupondto.AddCouponDto;
import com.dokong.board.repository.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.repository.dto.coupondto.UpdateCouponDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CouponServiceTest {


    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰_발급")
    public void addCoupon () throws Exception{
        // given
        AddCouponDto coupon = getCoupon();
        // when
        AddCouponResponseDto responseDto = couponService.addCoupon(coupon);
        // then
        assertThat(coupon.getCouponName()).isEqualTo(responseDto.getCouponName());
        assertThat(coupon.getCouponRate()).isEqualTo(responseDto.getCouponRate());
     }

     @Test
     @DisplayName("쿠폰_발급_예외")
     public void addCouponException () throws Exception{
         // given
         AddCouponDto coupon = getExceptionCoupon();
         // then
         assertThatThrownBy(() -> couponService.addCoupon(coupon))
                 .isExactlyInstanceOf(IllegalArgumentException.class)
                 .hasMessageContaining("할인율은 99보다 클 수 없습니다.");

      }
      
      @Test
      @DisplayName("벌크_쿠폰_수정_예외")
      public void bulkUpdateException () throws Exception{
          // given
          UpdateCouponDto coupon = getUpdateCoupon();

          // then
          assertThatThrownBy(() -> couponService.bulkUpdateCoupon(coupon))
                  .isExactlyInstanceOf(IllegalArgumentException.class)
                  .hasMessageContaining("해당 쿠폰은 존재하지 않습니다.");
       }
      

    private AddCouponDto getCoupon() {
        return AddCouponDto.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponDetail("회원가입을 축하하여 드리는 쿠폰입니다.")
                .couponRate(15)
                .minCouponPrice(10000)
                .build();
    }
    private AddCouponDto getExceptionCoupon() {
        return AddCouponDto.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponDetail("회원가입을 축하하여 드리는 쿠폰입니다.")
                .couponRate(101)
                .minCouponPrice(100)
                .build();
    }

    private UpdateCouponDto getUpdateCoupon() {
        return UpdateCouponDto.builder()
                .couponName("1번쿠폰")
                .couponRate(15)
                .build();
    }
}