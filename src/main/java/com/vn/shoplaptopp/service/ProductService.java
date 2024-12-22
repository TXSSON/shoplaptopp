package com.vn.shoplaptopp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vn.shoplaptopp.domain.*;
import com.vn.shoplaptopp.domain.dto.request.ProductCriteriaRequest;
import com.vn.shoplaptopp.repository.CartDetailRepository;
import com.vn.shoplaptopp.repository.CartRepository;
import com.vn.shoplaptopp.repository.ProductRepository;
import com.vn.shoplaptopp.repository.UserRepository;
import com.vn.shoplaptopp.service.specification.ProductSpecs;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartDetailRepository cartDetailRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
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

    public Page<Product> getAllPageProduct(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.productRepository.findAll(pageable);
    }

    public Page<Product> getAllProductsWithProductCriteria(ProductCriteriaRequest productCriteriaRequest, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        if(productCriteriaRequest.getSort() != null){
            if(productCriteriaRequest.getSort().equals("gia-tang-dan")){
                pageable = PageRequest.of(page, pageSize, Sort.by(Product_.PRICE).ascending());
            } else if(productCriteriaRequest.getSort().equals("gia-giam-dan")){
                pageable = PageRequest.of(page, pageSize, Sort.by(Product_.PRICE).descending());
            }
        }
        if (productCriteriaRequest == null) {
            return this.productRepository.findAll(pageable);
        }

        Specification<Product> combinedSpec = Specification.where(null);
        if (productCriteriaRequest.getTarget() != null ) {
            Specification<Product> currentSpecs = ProductSpecs.findProductByTarget(productCriteriaRequest.getTarget());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        if (productCriteriaRequest.getFactory() != null) {
            Specification<Product> currentSpecs = ProductSpecs.findProductByFactoryNameIn(productCriteriaRequest.getFactory());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        if (productCriteriaRequest.getPrice() != null) {
            Specification<Product> currentSpecs = this.buildPriceSpecification(productCriteriaRequest.getPrice());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        return this.productRepository.findAll(combinedSpec, pageable);

    }

    public Specification<Product> buildPriceSpecification(List<String> price) {
        Specification<Product> combinedSpec =  Specification.where(null);
        for (String p : price) {
            double min = 0;
            double max = 0;

            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "duoi-10-trieu":
                    min = 0;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.findProductByPriceBetween(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }

        return combinedSpec;
    }


    public Product getProductById(Long id) {
        return this.productRepository.findById(id).get();
    }

    public void handleDeleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(Long productId, HttpServletRequest request, Long quantity) {
        HttpSession session = request.getSession();
        User user = this.userRepository.findById((Long) session.getAttribute("id")).get();
        Product product = this.productRepository.findById(productId).get();
        Cart cart = this.cartRepository.findByUser(user);
        CartDetail cartDetail = new CartDetail();
        if (cart == null) {
            // Create new cart
            cart = new Cart();
            cart.setUser(user);
            cart.setSum(1);
            this.cartRepository.save(cart);
            // Create new cartDetail
            cartDetail.setProduct(product);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setCart(cart);
            cartDetail.setQuantity(quantity);
            this.cartDetailRepository.save(cartDetail);
            int afterSum = cart.getSum();
            session.setAttribute("sumOfCart", afterSum);
            return;
        }

        cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
        if (cartDetail == null) {
            cartDetail = new CartDetail();
            cartDetail.setProduct(product);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setCart(cart);
            cart.setSum(cart.getSum() + 1);
        }
        int afterSum = cart.getSum();
        session.setAttribute("sumOfCart", afterSum);
        cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        this.cartRepository.save(cart);
        this.cartDetailRepository.save(cartDetail);
    }

    public Long countAllProducts() {
        return this.productRepository.count();
    }


}
