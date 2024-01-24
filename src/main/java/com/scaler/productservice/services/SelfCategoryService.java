package com.scaler.productservice.services;

import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("SelfCategoryService")
public class SelfCategoryService implements CategoryService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Autowired
    public SelfCategoryService(CategoryRepository categoryRepository,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<String> names = new ArrayList<>();
        for(Category category : categories) {
            names.add(category.getName());
        }
        return names;
    }

    @Override
    public List<Product> getProductsInCategory(String name) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findCategoriesByName(name);
        if(categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Category with name: "+name+" is not found.");
        }

        return productRepository.findAllByCategory_Name(name);
    }
}
