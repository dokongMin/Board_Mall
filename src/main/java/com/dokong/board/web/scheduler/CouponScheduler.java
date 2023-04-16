//package com.dokong.board.web.scheduler;
//
//import com.dokong.board.web.service.redis.RedisCouponService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class CouponScheduler {
//
//    private final RedisCouponService redisCouponService;
//
//    @Scheduled(fixedDelay = 1000)
//    private void event() {
//        log.info("스케줄링 확인");
//        redisCouponService.publishFirstComeCoupon();
//        redisCouponService.checkWaiting();
//
//    }
//}
