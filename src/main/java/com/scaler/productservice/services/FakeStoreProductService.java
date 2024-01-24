package com.scaler.productservice.services;

import com.scaler.productservice.dtos.FakeStoreDto;
import com.scaler.productservice.dtos.FakeStoreDtoToProductConverter;
import com.scaler.productservice.dtos.ProductToFakeStoreDtoConverter;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

import static com.scaler.productservice.dtos.FakeStoreDtoToProductConverter.convertFakeStoreDtoToProduct;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreDto.class);

        if(fakeStoreDto == null) {
            throw new ProductNotFoundException("Product with "+id+" not found");
        }

        return FakeStoreDtoToProductConverter
                .convertFakeStoreDtoToProduct(fakeStoreDto);

    }

    /*
    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<List<FakeStoreDto>> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FakeStoreDto>>() {} );

        List<FakeStoreDto> fakeStoreDtos = responseEntity.getBody();
        List<Product> products = new ArrayList<>();
        for(FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            Product product = FakeStoreDtoToProductConverter
                                .convertFakeStoreDtoToProduct(fakeStoreDto);
            products.add(product);
        }

        return products;
    }

     */

    public List<Product> getAllProducts() {
        FakeStoreDto[] fakeStoreDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreDto[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            products.add(FakeStoreDtoToProductConverter
                    .convertFakeStoreDtoToProduct(fakeStoreDto));
        }
        return products;
    }

    @Override
    public Product addNewProduct(Product product) {
        FakeStoreDto fakeStoreDto = ProductToFakeStoreDtoConverter
                .convertProductToFakeStore(product);

        // HttpEntity class used to encapsulate header and body to be
        // sent to the restTemplate.postForObject method as a parameter
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(fakeStoreDto);

        // PostForObject(URL, RequestEntity encapsulating Header and Body,
        // Dto class used for JSON data transfer)
        fakeStoreDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                requestEntity, FakeStoreDto.class);

        return FakeStoreDtoToProductConverter
                .convertFakeStoreDtoToProduct(fakeStoreDto);
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreDto fakeStoreDto =
                ProductToFakeStoreDtoConverter
                        .convertProductToFakeStore(product);

        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(fakeStoreDto);

        fakeStoreDto = restTemplate.patchForObject(
                "https://fakestoreapi.com/products/" + id,
                requestEntity, FakeStoreDto.class);

        return FakeStoreDtoToProductConverter
                .convertFakeStoreDtoToProduct(fakeStoreDto);

    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        RequestCallback requestCallback =
                restTemplate.httpEntityCallback(
                        new HttpEntity<FakeStoreDto>(ProductToFakeStoreDtoConverter
                                                            .convertProductToFakeStore(product)),
                                                    FakeStoreDto.class);
        HttpMessageConverterExtractor<FakeStoreDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreDto.class,
                                                    restTemplate.getMessageConverters());
        return FakeStoreDtoToProductConverter.convertFakeStoreDtoToProduct
                (restTemplate.execute("https://fakestoreapi.com/products/"+id,
                HttpMethod.PUT, requestCallback, responseExtractor));
    }
}
