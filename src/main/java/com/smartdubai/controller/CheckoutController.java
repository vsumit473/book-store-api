package com.smartdubai.controller;

import com.smartdubai.dto.CheckoutResponseDto;
import com.smartdubai.entity.Checkout;
import com.smartdubai.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/smart/book/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponseDto> checkoutBooks(@RequestParam(required = false,name = "coupon") String coupon , @RequestBody List<Long> bookIds){

        CheckoutResponseDto checkoutResponseDto = checkoutService.processCheckout(bookIds, coupon);
        return new ResponseEntity<CheckoutResponseDto>(checkoutResponseDto, checkoutResponseDto.getStatusCode());
    }


}
