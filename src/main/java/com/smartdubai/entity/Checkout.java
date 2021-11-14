package com.smartdubai.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "checkout_id")
    private long id;

    @ManyToMany
    @JoinTable(name = "checkout_book",joinColumns = @JoinColumn(name = "checkout_id"),inverseJoinColumns = @JoinColumn(name = "book_id" ))
    private List<Book> bookList;

    @Column(name = "actual_price")
    private double actualPrice;

    @Column(name = "price_after_discount")
    private double discountedPrice;

    @OneToOne
    @JoinColumn(name = "coupon",referencedColumnName = "id")
    private PromotionalCoupon promotionalCoupon;

}
