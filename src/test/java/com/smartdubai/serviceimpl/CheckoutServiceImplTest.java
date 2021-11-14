package com.smartdubai.serviceimpl;

import com.smartdubai.dao.BookDao;
import com.smartdubai.dao.CheckoutDao;
import com.smartdubai.dao.PromotionalCouponDao;
import com.smartdubai.dto.CheckoutResponseDto;
import com.smartdubai.entity.Book;
import com.smartdubai.entity.Category;
import com.smartdubai.entity.Checkout;
import com.smartdubai.entity.PromotionalCoupon;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private CheckoutDao checkoutDao;

    @Mock
    private BookDao bookDao;

    @Mock
    private PromotionalCouponDao promotionalCouponDao;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void processCheckoutWhenNoPromoCodeIsApplied_AndWithValidBookIds() {

        Book book = new Book();
        book.setId(1);
        book.setPrice(100);
        book.setName("Mocked Book");
        Category category = new Category();
        category.setCategory("Mock category");
        category.setDiscount(10);
        category.setEnable(true);
        book.setType(category);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Optional<Book> optionalBook = Optional.of(book);
        Checkout checkout = new Checkout();
        checkout.setId(0);
        checkout.setActualPrice(100);
        checkout.setBookList(bookList);
        checkout.setDiscountedPrice(90);
        Checkout checkoutSaved = new Checkout();
        checkoutSaved.setActualPrice(100);
        checkoutSaved.setBookList(bookList);
        checkoutSaved.setDiscountedPrice(90);
        Mockito.when(bookDao.findById(book.getId())).thenReturn(optionalBook);

        Mockito.when(checkoutDao.save(checkout)).thenReturn(checkoutSaved);
        CheckoutResponseDto checkoutResponseDto = checkoutService.processCheckout(ids, null);
        assertEquals(checkoutResponseDto.getDiscountedPrice(),90);
        assertEquals(checkoutResponseDto.getActualPrice(),100);
        assertEquals(checkoutResponseDto.getMessage(),"Books are ready for placing an order");
        assertEquals(checkoutResponseDto.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void processCheckoutWhenNoPromoCodeIsApplied_AndWithInValidBookIds() {

        Book book = new Book();
        book.setId(1);
        book.setPrice(100);
        book.setName("Mocked Book");
        Category category = new Category();
        category.setCategory("Mock category");
        category.setDiscount(10);
        category.setEnable(true);
        book.setType(category);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Optional<Book> optionalBook = Optional.empty();
        Checkout checkout = new Checkout();
        checkout.setId(0);
        checkout.setActualPrice(100);
        checkout.setBookList(bookList);
        checkout.setDiscountedPrice(90);
        Checkout checkoutSaved = new Checkout();
        checkoutSaved.setActualPrice(100);
        checkoutSaved.setBookList(bookList);
        checkoutSaved.setDiscountedPrice(90);
        Mockito.when(bookDao.findById(book.getId())).thenReturn(optionalBook);
        CheckoutResponseDto checkoutResponseDto = checkoutService.processCheckout(ids, null);

        assertEquals(checkoutResponseDto.getMessage(),"Checkout failed , Following book ids are invalid/not found in database [1]");
        assertEquals(checkoutResponseDto.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void processCheckoutWhenPromoCodeIsApplied_AndWithValidBookIds() {

        Book book = new Book();
        book.setId(1);
        book.setPrice(100);
        book.setName("Mocked Book");
        Category category = new Category();
        category.setCategory("Mock category");
        category.setDiscount(10);
        category.setEnable(true);
        book.setType(category);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Optional<Book> optionalBook = Optional.of(book);
        Checkout checkout = new Checkout();
        checkout.setId(0);
        checkout.setActualPrice(100);
        checkout.setBookList(bookList);
        checkout.setDiscountedPrice(70);
        Checkout checkoutSaved = new Checkout();
        checkoutSaved.setActualPrice(100);
        checkoutSaved.setBookList(bookList);
        checkoutSaved.setDiscountedPrice(70);

        List<PromotionalCoupon> couponList = new ArrayList<>();
        PromotionalCoupon promotionalCoupon = new PromotionalCoupon();
        promotionalCoupon.setPromotionalCode("MOCKED");
        promotionalCoupon.setActive(true);
        promotionalCoupon.setDiscountAmount(20);
        couponList.add(promotionalCoupon);
        checkoutSaved.setPromotionalCoupon(promotionalCoupon);
        checkout.setPromotionalCoupon(promotionalCoupon);
        Mockito.when(bookDao.findById(book.getId())).thenReturn(optionalBook);

        Mockito.when(checkoutDao.save(checkout)).thenReturn(checkoutSaved);
        Mockito.when(promotionalCouponDao.findByPromotionalCode("MOCKED")).thenReturn(couponList);
        CheckoutResponseDto checkoutResponseDto = checkoutService.processCheckout(ids, "MOCKED");
        assertEquals(checkoutResponseDto.getDiscountedPrice(),70);
        assertEquals(checkoutResponseDto.getActualPrice(),100);
        assertEquals(checkoutResponseDto.getMessage(),"Books are ready for placing an order");
        assertEquals(checkoutResponseDto.getCouponStatus(),"Yay! Coupon applied successfully with discount of 20.0");
        assertEquals(checkoutResponseDto.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void processCheckoutWhenPromoCodeIsRedeemed_AndWithValidBookIds() {

        Book book = new Book();
        book.setId(1);
        book.setPrice(100);
        book.setName("Mocked Book");
        Category category = new Category();
        category.setCategory("Mock category");
        category.setDiscount(10);
        category.setEnable(true);
        book.setType(category);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Optional<Book> optionalBook = Optional.of(book);
        Checkout checkout = new Checkout();
        checkout.setId(0);
        checkout.setActualPrice(100);
        checkout.setBookList(bookList);
        checkout.setDiscountedPrice(90);
        Checkout checkoutSaved = new Checkout();
        checkoutSaved.setActualPrice(100);
        checkoutSaved.setBookList(bookList);
        checkoutSaved.setDiscountedPrice(90);

        List<PromotionalCoupon> couponList = new ArrayList<>();
        PromotionalCoupon promotionalCoupon = new PromotionalCoupon();
        promotionalCoupon.setPromotionalCode("MOCKED");
        promotionalCoupon.setActive(true);
        promotionalCoupon.setDiscountAmount(20);
        promotionalCoupon.setCouponUsed(true);
        couponList.add(promotionalCoupon);

        Mockito.when(bookDao.findById(book.getId())).thenReturn(optionalBook);

        Mockito.when(checkoutDao.save(checkout)).thenReturn(checkoutSaved);
        Mockito.when(promotionalCouponDao.findByPromotionalCode("MOCKED")).thenReturn(couponList);
        CheckoutResponseDto checkoutResponseDto = checkoutService.processCheckout(ids, "MOCKED");
        assertEquals(checkoutResponseDto.getDiscountedPrice(),90);
        assertEquals(checkoutResponseDto.getActualPrice(),100);
        assertEquals(checkoutResponseDto.getMessage(),"Books are ready for placing an order");
        assertEquals(checkoutResponseDto.getStatusCode(), HttpStatus.CREATED);
        assertEquals(checkoutResponseDto.getCouponStatus(),"Oops! Coupon has been redeemed");
    }
}