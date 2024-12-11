package com.vn.shoplaptopp.repository;

import java.util.List;
import java.util.Optional;

import com.vn.shoplaptopp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void deleteById(Long id);
}