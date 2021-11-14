package com.smartdubai.controller;


import com.smartdubai.entity.Category;
import com.smartdubai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/smart/types")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> retrieveAllCategories(){
            List<Category> categoryList =  categoryService.fetchAllCategories();
            return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
    }
}
