package com.scaler.productservice.controllers;

import com.scaler.productservice.dtos.FakeStoreDto;
import com.scaler.productservice.dtos.FakeStoreDtoToProductConverter;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(@Qualifier("selfProductService")
                                 ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id)
            throws ProductNotFoundException {
        return productService.getSingleProduct(id);
    }

    @PostMapping()
    public Product addNewProduct(@RequestBody FakeStoreDto fakeStoreDto) {
        return productService.addNewProduct(
                FakeStoreDtoToProductConverter
                .convertFakeStoreDtoToProduct(fakeStoreDto));
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id,
                                 @RequestBody FakeStoreDto fakeStoreDto) throws ProductNotFoundException {
        return productService.updateProduct(id,FakeStoreDtoToProductConverter
                .convertFakeStoreDtoToProduct(fakeStoreDto));
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id,
                                  @RequestBody FakeStoreDto fakeStoreDto)
            throws ProductNotFoundException {
        return productService.replaceProduct(id,FakeStoreDtoToProductConverter
                .convertFakeStoreDtoToProduct(fakeStoreDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
}
