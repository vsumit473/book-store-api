package com.smartdubai.serviceimpl;

import com.smartdubai.dao.CategoryDao;
import com.smartdubai.entity.Category;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {


    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void fetchAllCategories() {
        Category category = new Category();
        category.setCategory("mocked category");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        List<Category> categoryListFetched = categoryService.fetchAllCategories();
        assertEquals(categoryListFetched,categoryList);
    }
}