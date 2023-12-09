package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.CategoryEntity;
import com.API.Pizzapp.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@CrossOrigin("*")

public class CategoryController {

    private CategoryService categoryService;

@Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/CreateCategories")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @GetMapping("/GetCategories")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }
}
