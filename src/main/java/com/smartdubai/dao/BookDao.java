package com.smartdubai.dao;

import com.smartdubai.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao  extends JpaRepository<Book,Long> {

    @Modifying
    @Query(value = "update book set price = ?1 where book_id = ?2",nativeQuery = true)
    void updatePrice(double price,long id);

    @Modifying
    @Query(value = "delete from book where book_id = ?1",nativeQuery = true)
    void deleteBook(long id);
}
