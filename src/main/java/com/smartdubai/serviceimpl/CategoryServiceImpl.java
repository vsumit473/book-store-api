package com.smartdubai.serviceimpl;

import com.smartdubai.dao.CategoryDao;
import com.smartdubai.entity.Category;
import com.smartdubai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> fetchAllCategories() {
        return categoryDao.findAll();
    }
}
