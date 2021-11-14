package com.smartdubai.service;

import com.smartdubai.dto.BookResponseDto;
import com.smartdubai.entity.Book;
import com.smartdubai.exceptions.InvalidBookException;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookResponseDto> fetchAllBooks();

    Book addNewBook(Book book) throws InvalidBookException;

    Optional<Book> findById(Long id);

    void updateBook(Book book);

    void deleteBook(Book book);
}
