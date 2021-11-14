package com.smartdubai.dao;

import com.smartdubai.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutDao extends JpaRepository<Checkout, Long> {
}
