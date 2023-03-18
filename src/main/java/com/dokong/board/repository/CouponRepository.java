package com.dokong.board.repository;

import com.dokong.board.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByCouponName(String couponName);

    @Modifying(clearAutomatically = true)
    @Query("update Coupon c set c.couponRate = :couponRate where c.couponName = :couponName")
    int bulkUpdateCouponRate(@Param("couponRate") int couponRate, @Param("couponName") String couponName);
}
