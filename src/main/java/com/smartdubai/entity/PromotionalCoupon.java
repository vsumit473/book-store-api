package com.smartdubai.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
@Data
public class PromotionalCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "coupon_code")
    private String promotionalCode;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "has_used")
    private boolean isCouponUsed;

    @OneToOne(mappedBy = "promotionalCoupon", targetEntity = Checkout.class)
    private Checkout checkout;

    @Column(name = "discount_amount")
    private double discountAmount;
}
