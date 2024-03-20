package com.scaler.productservice.controllers;

import com.scaler.productservice.commons.AuthenticationCommons;
import com.scaler.productservice.dtos.FakeStoreDto;
import com.scaler.productservice.dtos.FakeStoreDtoToProductConverter;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService,
                             AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
//            @RequestHeader("AuthenticationToken") String token) {
//
//        if(authenticationCommons.validateToken(token) == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }

        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
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
