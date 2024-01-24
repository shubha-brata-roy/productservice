package com.scaler.productservice.repositories;

import com.scaler.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findById(Long id);

    public Product save(Product product);

    public void deleteById(Long id);

    public List<Product> findAll();

    public List<Product> findAllByCategory_Name(String name);

}
