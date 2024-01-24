package com.scaler.productservice.services;

import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.models.Product;

import java.util.List;

public interface CategoryService {
    public List<String> getAllCategories();

    List<Product> getProductsInCategory(String name) throws CategoryNotFoundException;
}
