package com.smartdubai.controller;

import com.smartdubai.dto.BookDto;
import com.smartdubai.dto.BookResponseDto;
import com.smartdubai.entity.Book;
import com.smartdubai.exceptions.DuplicateBookException;
import com.smartdubai.exceptions.GenericBookException;
import com.smartdubai.exceptions.InvalidBookException;
import com.smartdubai.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart/book")
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> retrieveAllBooks(){

        log.info("request came to retrieveAllBooks()");
        List<BookResponseDto> bookList = bookService.fetchAllBooks();
        return new ResponseEntity<List<BookResponseDto>>(bookList, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<BookDto> addNewBook(@RequestBody Book book) throws InvalidBookException, DuplicateBookException {
        try{
            log.info("request came to addNewBook()");

            Book savedBook = bookService.addNewBook(book);
            BookDto bookDto = new BookDto();
            bookDto.setBook(savedBook);
            bookDto.setMessage("Book is saved successfully with id :"+ savedBook.getId());
            return new ResponseEntity<BookDto>(bookDto,HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e){
            log.error("Duplicate book was being inserted hence throwing exception");
            throw new DuplicateBookException(e);
        }
        catch (Exception e){

            log.error("Invalid book with name as error "+book.getName());
            throw new InvalidBookException(e);
        }

    }

    @PutMapping
    public ResponseEntity<BookDto> updateExistingBook(@RequestBody Book book) throws GenericBookException {
        try{
            String message = "";
            Optional<Book> bookInDatabase = bookService.findById(book.getId());
            BookDto bookDto = new BookDto();
            if(!bookInDatabase.isPresent()){
                message = "Book with the provided id "+book.getId() + " not found in database";
            }
            else{
                bookService.updateBook(book);
                bookDto.setBook(book);
                message = "Book with the provided id "+book.getId() + " updated successfully!";
            }
            bookDto.setMessage(message);

            return new ResponseEntity<BookDto>(bookDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            throw new GenericBookException(e);
        }

    }

    @DeleteMapping
    public ResponseEntity<BookDto> deleteBook(@RequestBody Book book) throws InvalidBookException {
        try{
            String message = "";
            Optional<Book> bookInDatabase = bookService.findById(book.getId());
            BookDto bookDto = new BookDto();
            if(!bookInDatabase.isPresent()){
                message = "Book with the provided id "+book.getId() + " not found in database";
            }else{
                bookService.deleteBook(book);
                bookDto.setBook(book);
                message = "Book with the provided id "+book.getId() + " deleted successfully!";
            }
            bookDto.setMessage(message);
            return new ResponseEntity<BookDto>(bookDto,HttpStatus.CREATED);
        }catch (Exception e){
            throw new InvalidBookException(e);
        }

    }

}
