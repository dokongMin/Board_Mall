package com.dokong.board.web.service.redis;

import com.dokong.board.domain.user.User;
import com.dokong.board.web.dto.eventcoupon.EventCoupon;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisCouponServiceTest {

    @Autowired
    private RedisCouponService redisCouponService;

    @Autowired
    private RedissonLockFacade redissonLockFacade;

    @Autowired
    private UserService userService;

    private final int REPEAT = 100;
    private final String COUPON_NAME = "EVENTCOUPON";
    private final int LIMIT = 30;

    @Test
    public void eventCouponTest() throws Exception {

        EventCoupon eventCouponEntity = getEventCoupon();

        for (int i = 0; i < REPEAT; i++) {
            JoinUserDto joinUserDto = getJoinUserDto(i + 1);
            userService.saveUser(joinUserDto);
        }

        List<User> allUser = userService.findAllUser();
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    Random random = new Random();
                    random.setSeed(System.currentTimeMillis());
                    int rand = (int) Math.floor(Math.random() * REPEAT);
                    redisCouponService.addQueue(eventCouponEntity.getCouponName(), allUser.get(rand).getUsername());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        ExecutorService executorService2 = Executors.newFixedThreadPool(10);
        CountDownLatch latch2 = new CountDownLatch(threadCount);
        for (int i = 0; i < 100; i++) {
            executorService2.submit(() -> {
                try {
                    redisCouponService.publishFirstComeCoupon(eventCouponEntity);
                    redisCouponService.checkWaiting(eventCouponEntity.getCouponName());
                } finally {
                    latch2.countDown();
                }
            });
        }
        latch2.await();

    }

    private EventCoupon getEventCoupon() {
        return EventCoupon.builder()
                .couponName(COUPON_NAME)
                .couponDetail("선착순으로 발급된 쿠폰입니다.")
                .couponRate(20)
                .minCouponPrice(10000)
                .limit(LIMIT)
                .build();

    }

    private JoinUserDto getJoinUserDto(int i) {
        return JoinUserDto.builder()
                .username("user" + i)
                .password("aa" + i)
                .name("min" + i)
                .email("alsghks@naver.com" + i)
                .build();
    }

}