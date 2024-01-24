package com.scaler.productservice.services;

import com.scaler.productservice.dtos.FakeStoreDto;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;

    Product addNewProduct(Product product);

    void deleteProduct(Long id);

    Product updateProduct(Long id, Product product) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product replaceProduct(Long id, Product product) throws ProductNotFoundException;
}
