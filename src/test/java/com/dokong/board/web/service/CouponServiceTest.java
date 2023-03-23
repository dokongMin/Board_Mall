package com.dokong.board.web.service;

import com.dokong.board.domain.user.User;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.coupondto.AddCouponResponseDto;
import com.dokong.board.web.dto.coupondto.UpdateCouponDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import com.dokong.board.web.service.CouponService;
import com.dokong.board.web.service.LoginService;
import com.dokong.board.web.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CouponServiceTest {


    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("쿠폰_발급")
    public void addCoupon() throws Exception {
        // given
        JoinUserDto userDto = getUserDto();
        userService.saveUser(userDto);

        LoginUserDto loginUserDto = getLoginUserDto(userDto);

        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
        User user = userRepository.findByUsername(sessionUserDto.getUsername()).get();

        AddCouponDto coupon = getCoupon();
        // when
        AddCouponResponseDto responseDto = couponService.addCoupon(coupon, sessionUserDto);
        // then
        assertThat(coupon.getCouponName()).isEqualTo(responseDto.getCouponName());
        assertThat(coupon.getCouponRate()).isEqualTo(responseDto.getCouponRate());

        assertThat(user.getCoupons().size()).isEqualTo(1);
        assertThat(user.getCoupons().get(0).getCouponName()).isEqualTo("회원가입 축하 쿠폰");
    }


    @Test
    @DisplayName("쿠폰_발급_예외")
    public void addCouponException() throws Exception {
        // given
        JoinUserDto userDto = getUserDto();
        userService.saveUser(userDto);

        LoginUserDto loginUserDto = getLoginUserDto(userDto);

        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
        AddCouponDto coupon = getExceptionCoupon();
        // then
        assertThatThrownBy(() -> couponService.addCoupon(coupon, sessionUserDto))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인율은 99보다 클 수 없습니다.");

    }

    @Test
    @DisplayName("벌크_쿠폰_수정")
    @Rollback(value = false)
    public void bulkUpdateCoupon() throws Exception {
        // given
        JoinUserDto userDto = getUserDto();
        userService.saveUser(userDto);

        LoginUserDto loginUserDto = getLoginUserDto(userDto);

        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
        User user = userRepository.findByUsername(sessionUserDto.getUsername()).get();

        AddCouponDto coupon1 = getCoupon();
        AddCouponDto coupon2 = getCoupon();
        AddCouponResponseDto responseDto1 = couponService.addCoupon(coupon1, sessionUserDto);
        AddCouponResponseDto responseDto2 = couponService.addCoupon(coupon2, sessionUserDto);

        // then
        UpdateCouponDto updateCouponDto = getUpdateCoupon();
        couponService.bulkUpdateCoupon(updateCouponDto);
        User perUser = userService.findById(user.getId());
        assertThat(perUser.getCoupons().get(0).getCouponRate()).isEqualTo(40);
        assertThat(perUser.getCoupons().get(1).getCouponRate()).isEqualTo(40);

    }


    @Test
    @DisplayName("벌크_쿠폰_수정_예외")
    public void bulkUpdateException() throws Exception {
        // given
        UpdateCouponDto coupon = getUpdateCouponEx();

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

    private UpdateCouponDto getUpdateCouponEx() {
        return UpdateCouponDto.builder()
                .couponName("1번쿠폰")
                .couponRate(15)
                .build();
    }

    private UpdateCouponDto getUpdateCoupon() {
        return UpdateCouponDto.builder()
                .couponName("회원가입 축하 쿠폰")
                .couponRate(40)
                .build();
    }


    private LoginUserDto getLoginUserDto(JoinUserDto userDto) {
        return LoginUserDto.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

    private JoinUserDto getUserDto() {
        return JoinUserDto.builder()
                .username("aaa")
                .password("bbb")
                .build();
    }
}