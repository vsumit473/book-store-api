package com.smartdubai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "discount")
    private double discount;

    @Column(name = "isenable")
    private boolean isEnable;

    @OneToMany(mappedBy = "type",targetEntity = Book.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Book> book;
}
