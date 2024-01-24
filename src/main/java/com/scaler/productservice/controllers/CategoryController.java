package com.scaler.productservice.controllers;

import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(@Qualifier("SelfCategoryService") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getAllProductsInCategory(@PathVariable("categoryName") String name)
            throws CategoryNotFoundException {
        return categoryService.getProductsInCategory(name);
    }
}
