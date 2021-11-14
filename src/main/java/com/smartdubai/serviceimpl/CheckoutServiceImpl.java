package com.smartdubai.serviceimpl;

import com.smartdubai.dao.BookDao;
import com.smartdubai.dao.CheckoutDao;
import com.smartdubai.dao.PromotionalCouponDao;
import com.smartdubai.dto.CheckoutResponseDto;
import com.smartdubai.entity.Book;
import com.smartdubai.entity.Checkout;
import com.smartdubai.entity.PromotionalCoupon;
import com.smartdubai.service.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutDao checkoutDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private PromotionalCouponDao promotionalCouponDao;

    @Override
    public CheckoutResponseDto processCheckout(List<Long> bookIds,String coupon) {

        log.info("processing checkout");
        Checkout checkout = new Checkout();
        List<Book> bookList = new ArrayList<>(0);
        CheckoutResponseDto checkoutResponseDto = null;
        List<Long> invalidBookIds   = new ArrayList<>(0);
        double actualPrice = 0;
        double discountedPrice = 0;
        double couponDiscount = 0;
        String couponStatus = null;

        for(Long bookId : bookIds){
            Optional<Book> book = bookDao.findById(bookId);
            if(book.isPresent()){
                bookList.add(book.get());
                actualPrice += book.get().getPrice();
                double discountOnCurrentBook = 0;
                if(book.get().getType().isEnable()){
                    discountOnCurrentBook= (book.get().getType().getDiscount()/100)*book.get().getPrice();
                }

                discountedPrice +=  (book.get().getPrice()-discountOnCurrentBook);
            }
            else{
                invalidBookIds.add(bookId);
            }

        }

        if(!CollectionUtils.isEmpty(invalidBookIds)){
            checkoutResponseDto = new CheckoutResponseDto();
            checkoutResponseDto.setMessage("Checkout failed , Following book ids are invalid/not found in database " + Arrays.toString(invalidBookIds.toArray()));
            checkoutResponseDto.setStatusCode(HttpStatus.BAD_REQUEST);
            return checkoutResponseDto;
        }

        checkout.setActualPrice(actualPrice);
        checkout.setDiscountedPrice(discountedPrice);
        checkout.setBookList(bookList);
        List<PromotionalCoupon> promotionalCoupon = null;
        if(null != coupon && !coupon.isEmpty()){
             promotionalCoupon = promotionalCouponDao.findByPromotionalCode(coupon);
             if(!CollectionUtils.isEmpty(promotionalCoupon) && !promotionalCoupon.get(0).isCouponUsed()){
                 couponDiscount = promotionalCoupon.get(0).getDiscountAmount();
                 discountedPrice = checkout.getDiscountedPrice()-couponDiscount;
                 checkout.setDiscountedPrice(discountedPrice);
                 checkout.setPromotionalCoupon(promotionalCoupon.get(0));
                 promotionalCoupon.get(0).setCouponUsed(true);
                 promotionalCouponDao.saveAll(promotionalCoupon);
                 couponStatus = "Yay! Coupon applied successfully with discount of "+couponDiscount;
             }
             else if(!CollectionUtils.isEmpty(promotionalCoupon) && promotionalCoupon.get(0).isCouponUsed()){
                 couponStatus = "Oops! Coupon has been redeemed";
             }


        }
        checkout.setDiscountedPrice(discountedPrice<0?0 : discountedPrice);

        checkoutResponseDto = saveCheckoutDetails(checkout);
        checkoutResponseDto.setCouponDiscount(couponDiscount);
        checkoutResponseDto.setCouponStatus(couponStatus);
        return checkoutResponseDto;


    }

    private CheckoutResponseDto saveCheckoutDetails(Checkout checkout) {
        Checkout checkedOutDetails = checkoutDao.save(checkout);
        CheckoutResponseDto checkoutResponseDto = new CheckoutResponseDto();
        checkoutResponseDto.setActualPrice(checkedOutDetails.getActualPrice());
        checkoutResponseDto.setDiscountedPrice(checkedOutDetails.getDiscountedPrice());
        if(null!= checkedOutDetails.getPromotionalCoupon()){
            checkoutResponseDto.setCouponCodeApplied(checkedOutDetails.getPromotionalCoupon().getPromotionalCode());
        }

        return  checkoutResponseDto;
    }
}
