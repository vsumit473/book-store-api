package com.smartdubai.serviceimpl;

import com.smartdubai.dao.BookDao;
import com.smartdubai.dao.CategoryDao;
import com.smartdubai.dto.BookResponseDto;
import com.smartdubai.entity.Book;
import com.smartdubai.entity.Category;
import com.smartdubai.exceptions.InvalidBookException;
import com.smartdubai.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryDao categoryDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<BookResponseDto> fetchAllBooks() {
        log.info("fetching all books");
        List<Book> bookList = bookDao.findAll();

        List<BookResponseDto> bookResponseDtoList = bookList.stream().map(book -> modelMapper.map(book,BookResponseDto.class)).collect(Collectors.toList());
        return bookResponseDtoList;
    }

    @Override
    public Book addNewBook(Book book) throws InvalidBookException {
        log.info("Adding new book with name "+ book.getName());
        if(Objects.isNull(book.getName()) || book.getName().isEmpty()){
            throw new InvalidBookException("Provided book data is not valid");
        }
        else{
            List<Category> categoryList = categoryDao.findByCategory(book.getCategoryName());
            book.setType(categoryList.get(0));
            return bookDao.save(book);
        }

    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookDao.updatePrice(book.getPrice(),book.getId());
    }

    @Override
    @Transactional
    public void deleteBook(Book book) {
        bookDao.deleteBook(book.getId());
    }
}
