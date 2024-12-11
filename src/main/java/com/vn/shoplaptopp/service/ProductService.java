package com.vn.shoplaptopp.service;

import java.util.List;

import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.repository.ProductRepository;
import org.springframework.stereotype.Service;



@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void handleSaveProduct(Product product) {
        if (product.getId() == null) {
            this.productRepository.save(product);
        }
        Product currentProduct = this.productRepository.findById(product.getId()).get();
        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setDetailDesc(product.getDetailDesc());
        currentProduct.setFactory(product.getFactory());
        currentProduct.setQuantity(product.getQuantity());
        currentProduct.setImage(product.getImage());
        currentProduct.setShortDetail(product.getShortDetail());
        currentProduct.setTarget(product.getTarget());
        this.productRepository.save(currentProduct);
    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id).get();
    }

    public void handleDeleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }
}
