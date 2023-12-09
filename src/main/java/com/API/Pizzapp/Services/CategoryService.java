package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.CategoryEntity;
import java.util.List;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);
    List<CategoryEntity> getAllCategories();
}
