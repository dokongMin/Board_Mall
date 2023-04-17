package com.dokong.board.web.service.redis;

import com.dokong.board.web.dto.eventcoupon.EventCoupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockFacade {

    private final RedisCouponService redisCouponService;

    private final RedissonClient redissonClient;

    public void callRedisService(EventCoupon eventCoupon) {
        RLock lock = redissonClient.getLock("COUPON_KEY");
        try {
            boolean isLocked = lock.tryLock(2, 1, TimeUnit.SECONDS);
            if (!isLocked) {
                log.info("error");
                throw new IllegalArgumentException("Lock error");
            }
            redisCouponService.publishFirstComeCoupon(eventCoupon);
//            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
