package com.dokong.board.web.service.redis;

import com.dokong.board.domain.coupon.CouponStatus;
import com.dokong.board.domain.user.User;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.eventcoupon.EventCoupon;
import com.dokong.board.web.service.CouponService;
import com.dokong.board.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCouponService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long LAST_INDEX = 9;

    private final CouponService couponService;
    private final UserService userService;

    public void addQueue(String couponName, String username) {
        long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(couponName, username, now);
    }

    public void checkWaiting(String couponName) {
        Set<Object> queue = redisTemplate.opsForZSet().range(couponName, FIRST_ELEMENT, LAST_ELEMENT);
        for (Object user : queue) {
            Long rank = redisTemplate.opsForZSet().rank(couponName, user);
            log.info("'{}' 님의 현재 대기열은 {} 명 남았습니다.", user, rank);
        }
    }

    public Boolean publishFirstComeCoupon(EventCoupon eventCoupon) {
        Set<Object> queue = redisTemplate.opsForZSet().range(eventCoupon.getCouponName(), FIRST_ELEMENT, LAST_INDEX);
        if (eventCoupon.endCount()) {
            log.info("선착순 이벤트가 종료되었습니다. 남은 쿠폰 개수 : {}", eventCoupon.getLimit());
            redisTemplate.opsForZSet().removeRange(eventCoupon.getCouponName(), FIRST_ELEMENT, LAST_ELEMENT);
            return false;
        }
        for (Object user : queue) {
            couponService.addEventCoupon(eventCoupon, user.toString());
            log.info("'{}' 님에게 쿠폰이 발급됐습니다.", user);
            redisTemplate.opsForZSet().remove(eventCoupon.getCouponName(), user);
            eventCoupon.decreaseCount();
        }
        return true;
    }
}
