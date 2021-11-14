package com.smartdubai.dao;

import com.smartdubai.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<Category,Integer> {

    @Query(value = "select * from category c where c.category = ?1",nativeQuery = true)
    List<Category> findByCategory(String categoryName);

}
