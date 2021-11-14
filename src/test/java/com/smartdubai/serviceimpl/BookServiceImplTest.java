package com.smartdubai.serviceimpl;

import com.smartdubai.dao.BookDao;
import com.smartdubai.dao.CategoryDao;
import com.smartdubai.dto.BookResponseDto;
import com.smartdubai.entity.Book;
import com.smartdubai.entity.Category;
import com.smartdubai.exceptions.InvalidBookException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryDao categoryDao;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void fetchAllBooks() {
        List<Book> mockedList = new ArrayList<>();
        Book book = new Book();
        book.setName("test book");
        book.setAuthor("test author");
        book.setPrice(0);
        mockedList.add(book);
        Mockito.when(bookDao.findAll()).thenReturn(mockedList);
        List<BookResponseDto> bookResponseDtoList = bookService.fetchAllBooks();
        assertEquals(bookResponseDtoList.size(),1);

    }

    @Test
    void addNewBookWhenNameIsNull() throws InvalidBookException {
        Book book = new Book();
        book.setName(null);
        book.setAuthor("test author");
        book.setPrice(0);
        Throwable exception = assertThrows(InvalidBookException.class, () -> bookService.addNewBook(book));
        assertEquals("Provided book data is not valid", exception.getMessage());

    }

    @Test
    void addNewBookWhenNameIsEmpty() throws InvalidBookException {
        Book book = new Book();
        book.setName("");
        book.setAuthor("test author");
        book.setPrice(0);
        Throwable exception = assertThrows(InvalidBookException.class, () -> bookService.addNewBook(book));
        assertEquals("Provided book data is not valid", exception.getMessage());

    }

    @Test
    void addNewBookWhenNameIsProper() throws InvalidBookException {
        Book book = new Book();
        book.setName("test Book name");
        book.setAuthor("test author");
        book.setPrice(0);
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        category.setCategory("Mocked Category");
        categoryList.add(category);
        Mockito.when(categoryDao.findByCategory(book.getCategoryName())).thenReturn(categoryList);
        Mockito.when(bookDao.save(book)).thenReturn(book);
        Book bookSaved = bookService.addNewBook(book);
        assertEquals(bookSaved,book);

    }

    @Test
    void findById() {
        Book book = new Book();
        book.setName("test Book name");
        book.setAuthor("test author");
        book.setPrice(0);
        Optional<Book> bookOptional = Optional.of(book);
        Mockito.when(bookDao.findById(1L)).thenReturn(bookOptional);
        Optional<Book> bookFoundById = bookService.findById(1L);
        assertEquals(bookFoundById.get(),book);

    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setName("test Book name");
        book.setAuthor("test author");
        book.setPrice(0);
        Mockito.doNothing().when(bookDao).updatePrice(book.getPrice(),book.getId());
        bookService.updateBook(book);

    }

    @Test
    void deleteBook() {
        Book book = new Book();
        book.setName("test Book name");
        book.setAuthor("test author");
        book.setPrice(0);
        Mockito.doNothing().when(bookDao).deleteBook(book.getId());
        bookService.deleteBook(book);
    }
}