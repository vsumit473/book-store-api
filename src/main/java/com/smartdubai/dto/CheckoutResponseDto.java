package com.smartdubai.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CheckoutResponseDto {

    private String message="Books are ready for placing an order";

    private double actualPrice;

    private double discountedPrice;

    private String  couponCodeApplied;

    private HttpStatus statusCode = HttpStatus.CREATED;

    private double couponDiscount;

    private String couponStatus;
}
