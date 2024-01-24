package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;

public class FakeStoreDtoToProductConverter {
    public static Product convertFakeStoreDtoToProduct(FakeStoreDto fakeStoreDto) {
        Product product = new Product();
        product.setId(fakeStoreDto.getId());
        product.setTitle(fakeStoreDto.getTitle());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreDto.getCategory());
        product.setPrice(fakeStoreDto.getPrice());
        product.setDescription(fakeStoreDto.getDescription());
        product.setImageUrl(fakeStoreDto.getImage());

        return product;
    }
}
