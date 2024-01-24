package com.scaler.productservice.services;

import com.scaler.productservice.dtos.FakeStoreDto;
import com.scaler.productservice.dtos.FakeStoreDtoToProductConverter;
import com.scaler.productservice.exceptions.CategoryNotFoundException;
import com.scaler.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreCategoryService")
public class FakeStoreCategoryService implements CategoryService {
    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getAllCategories() {
        ResponseEntity<List<String>> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {} );
        return response.getBody();
    }

    @Override
    public List<Product> getProductsInCategory(String name)
            throws CategoryNotFoundException {
        ResponseEntity<List<FakeStoreDto>> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/category/" + name,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FakeStoreDto>>() {});

        List<FakeStoreDto> fakeStoreDtos = response.getBody();
        List<Product> products = new ArrayList<>();

        for(FakeStoreDto fakeStoreDto : fakeStoreDtos) {
            products.add(FakeStoreDtoToProductConverter
                        .convertFakeStoreDtoToProduct(fakeStoreDto));
        }

        if(products.isEmpty()) {
            throw new CategoryNotFoundException("Category "+name+" doesn't exist");
        }

        return products;
    }
}
