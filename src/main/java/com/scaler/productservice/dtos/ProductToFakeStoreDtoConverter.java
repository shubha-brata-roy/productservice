package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Product;

public class ProductToFakeStoreDtoConverter {
    public static FakeStoreDto convertProductToFakeStore(Product product) {
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setId(product.getId());
        fakeStoreDto.setCategory(product.getCategory().getName());
        fakeStoreDto.setDescription(product.getDescription());
        fakeStoreDto.setPrice(product.getPrice());
        fakeStoreDto.setImage(product.getImageUrl());
        fakeStoreDto.setTitle(product.getTitle());

        return fakeStoreDto;
    }
}
