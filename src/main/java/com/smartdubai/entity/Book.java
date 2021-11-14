package com.smartdubai.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "book")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type",referencedColumnName = "id")
    @JsonIgnore
    private Category type;

    @Column(name = "price")
    private double price;

    @Column(name = "isbn")
    private long isbn;

    @ManyToMany(mappedBy = "bookList")
    private List<Checkout> checkout;

    //@JsonIgnore
    private String categoryName;
}
