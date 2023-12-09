package com.API.Pizzapp.Services.Impl;

import com.API.Pizzapp.Models.CategoryEntity;
import com.API.Pizzapp.Repository.CategoryRepository;
import com.API.Pizzapp.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
