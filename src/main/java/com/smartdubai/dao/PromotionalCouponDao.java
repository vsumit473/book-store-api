package com.smartdubai.dao;

import com.smartdubai.entity.PromotionalCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionalCouponDao extends JpaRepository<PromotionalCoupon, Long> {

    @Query(value = "select * from coupon where coupon_code = :coupon",nativeQuery = true)
    List<PromotionalCoupon> findByPromotionalCode(String coupon);
}
