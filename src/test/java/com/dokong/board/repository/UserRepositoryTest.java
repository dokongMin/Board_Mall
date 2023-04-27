package com.dokong.board.repository;

import com.dokong.board.domain.coupon.Coupon;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.NotEnoughTimeException;
import com.dokong.board.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CouponRepository couponRepository;

    @Test
    @DisplayName("회원_저장")
    public void memberSave () throws Exception{
        // given
        User user = getUser();
        // when
        User savedUser = userRepository.save(user);
        // then
        assertThat(savedUser).isEqualTo(user);
     }


    @Test
    @DisplayName("회원_쿠폰_발급")
    public void couponIssueException () throws Exception{
        // given
        User user = getUser();
        userRepository.save(user);

        Coupon coupon = getCoupon();
        couponRepository.save(coupon);
        // then
        user.addCoupon(coupon);
        assertThat(user.getCoupons().get(0).getCouponName()).isEqualTo("회원가입 축하 쿠폰");
        assertThat(user.getCoupons().size()).isEqualTo(1);
    }


//    @Test
//    @DisplayName("회원_쿠폰_발급_예외")
//     public void couponIssueException () throws Exception{
//         // given
//        User user = getUser();
//        userRepository.save(user);
//
//        Coupon coupon = getCoupon();
//        couponRepository.save(coupon);
//         // then
//        assertThatThrownBy(() -> user.addCoupon(coupon))
//                .isExactlyInstanceOf(NotEnoughTimeException.class)
//                .hasMessageContaining("쿠폰은 회원 가입 후, 하루가 지나야 발급 가능합니다.");
//      }

    private Coupon getCoupon() {
        return Coupon.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponDetail("회원가입을 축하하여 드리는 쿠폰입니다.")
                .couponRate(15)
                .minCouponPrice(10000)
                .build();
    }
    private User getUser() {
        User user = User.builder()
                .username("정민환")
                .password("1234")
                .build();
        return user;
    }
}