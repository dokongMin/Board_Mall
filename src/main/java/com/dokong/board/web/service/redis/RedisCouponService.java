package com.dokong.board.web.service.redis;

import com.dokong.board.web.dto.eventcoupon.EventCoupon;
import com.dokong.board.web.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCouponService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long LAST_INDEX = 9;

    private final CouponService couponService;

    private final RedissonClient redissonClient;

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

    public void publishFirstComeCoupon(EventCoupon eventCoupon) {
        RLock lock = redissonClient.getLock("COUPON_KEY");
        try {
            boolean isLocked = lock.tryLock(10, 3, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new IllegalArgumentException("Lock error");
            }
            Set<Object> queue = redisTemplate.opsForZSet().range(eventCoupon.getCouponName(), FIRST_ELEMENT, LAST_INDEX);
            if (eventCoupon.endCount()) {
                log.info("선착순 이벤트가 종료되었습니다. 남은 쿠폰 개수 : {}", eventCoupon.getLimit());
                redisTemplate.opsForZSet().removeRange(eventCoupon.getCouponName(), FIRST_ELEMENT, LAST_ELEMENT);
                throw new RuntimeException("Not Enough Coupons");
            }
            for (Object user : queue) {
                couponService.addEventCoupon(eventCoupon, user.toString());
                log.info("'{}' 님에게 쿠폰이 발급됐습니다.", user);
                redisTemplate.opsForZSet().remove(eventCoupon.getCouponName(), user);
                eventCoupon.decreaseCount();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
