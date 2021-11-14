package com.smartdubai.dto;
import com.smartdubai.entity.Book;
import lombok.Data;

import java.io.Serializable;


@Data
public class BookResponseDto implements Serializable {


    private long id;

    private String name;

    private String description;

    private String author;

    //private String type;

    private double price;

    private long isbn;

}
