package com.smartdubai.service;

import com.smartdubai.dto.CheckoutResponseDto;
import com.smartdubai.entity.Checkout;

import java.util.List;

public interface CheckoutService {


    CheckoutResponseDto processCheckout(List<Long> bookIds,String coupon);
}
