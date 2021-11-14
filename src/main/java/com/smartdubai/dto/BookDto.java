package com.smartdubai.dto;

import com.smartdubai.entity.Book;
import lombok.Data;

@Data
public class BookDto {

    private String message;

    private Book book;
}
