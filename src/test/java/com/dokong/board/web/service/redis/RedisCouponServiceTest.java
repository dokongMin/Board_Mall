package com.dokong.board.web.service.redis;

import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.user.UserRedisRepository;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.eventcoupon.EventCoupon;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.JoinUserResponseDto;
import com.dokong.board.web.dto.userdto.UserDtoRedis;
import com.dokong.board.web.service.CouponService;
import com.dokong.board.web.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisCouponServiceTest {

    @Autowired
    private RedisCouponService redisCouponService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    private final int REPEAT = 100;
    private final String COUPON_NAME = "EVENT_COUPON";
    private final int LIMIT = 30;

    @Test
//    @Transactional
//    @Rollback(value = false)
    public void eventCouponTest() throws Exception {

        for (int i = 0; i < REPEAT; i++) {

            JoinUserDto joinUserDto = getJoinUserDto(i);
            JoinUserResponseDto joinUserResponseDto = userService.saveUser(joinUserDto);

            AddCouponDto eventCoupon = getAddCouponDto(joinUserResponseDto.getId());
            couponService.addCoupon(eventCoupon);
        }

        EventCoupon eventCoupon = new EventCoupon(COUPON_NAME, LIMIT);
        redisCouponService.setEventCoupon(eventCoupon);

        List<User> allUser = userService.findAllUser();
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try{
                    Random random = new Random();
                    random.setSeed(System.currentTimeMillis());
                    int rand = (int) Math.floor(Math.random() * REPEAT);
                    redisCouponService.addQueue(COUPON_NAME, allUser.get(rand).getUsername());
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        while (redisCouponService.publishFirstComeCoupon(COUPON_NAME)) {
//            redisCouponService.publishFirstComeCoupon(COUPON_NAME);
            redisCouponService.checkWaiting(COUPON_NAME);
        }
    }

    private JoinUserDto getJoinUserDto(int i) {
        return JoinUserDto.builder()
                .username("user" + i)
                .password("aa" + i)
                .name("min" + i)
                .email("alsghks@naver.com" + i)
                .build();
    }

    private AddCouponDto getAddCouponDto(Long id) {
        AddCouponDto eventCoupon = AddCouponDto.builder()
                .couponName(COUPON_NAME)
                .couponDetail("선착순으로 발급된 쿠폰입니다.")
                .couponRate(20)
                .minCouponPrice(10000)
                .userId(id)
                .build();
        return eventCoupon;
    }

}