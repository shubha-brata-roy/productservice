package com.scaler.productservice.services;

import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product with id: "+id+
                                                " not found.");
        }
        return productOptional.get();
    }

    @Override
    public Product addNewProduct(Product product) {
        Category category = saveUpdateCategoryByName(product.getCategory().getName());

        product.setCategory(category);
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product with id: "+id+" not found. " +
                                                "Please enter a valid id to update.");
        }
        Product savedProduct = productOptional.get();

        // bug - new category is getting created when left null - fix it!
        if(product.getCategory().getName() != null) {
            Category category = saveUpdateCategoryByName(product.getCategory().getName());
            savedProduct.setCategory(category);
        }

        if(product.getTitle() != null) {
            savedProduct.setTitle(product.getTitle());
        }

        if(product.getDescription() != null) {
            savedProduct.setDescription(product.getDescription());
        }

        if(product.getImageUrl() != null) {
            savedProduct.setImageUrl(product.getImageUrl());
        }

        if(product.getPrice() != null) {
            savedProduct.setPrice(product.getPrice());
        }

        return productRepository.save(savedProduct);
    }

    @Override
    public List<Product> getAllProducts() {
//        List<Product> products = productRepository.findAll();
        return productRepository.findAll();
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product with id: "+id+" not found.");
        }
        Product savedProduct = productOptional.get();

        savedProduct.setTitle(product.getTitle());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setDescription(product.getDescription());

        // what if the category doesn't exist
        // need to create in that case

        Category category = saveUpdateCategoryByName(product.getCategory().getName());

        savedProduct.setCategory(category);

        savedProduct.setImageUrl(product.getImageUrl());

        return productRepository.save(savedProduct);
    }

    private Category saveUpdateCategoryByName(String name) {
        Optional<Category> categoryOptional = categoryRepository.findCategoriesByName(name);
        Category category = new Category();

        if(categoryOptional.isEmpty()) {
            category.setName(name);
            category = categoryRepository.save(category);
        } else {
            category = categoryOptional.get();
        }
        return category;
    }
}
