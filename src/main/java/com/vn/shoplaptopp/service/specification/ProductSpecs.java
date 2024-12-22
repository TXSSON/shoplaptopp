package com.vn.shoplaptopp.service.specification;

import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.domain.Product_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecs {

    public static Specification<Product> findProductByPriceBetween (double minPrice, double maxPrice) {
        return new Specification<Product>() {
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get(Product_.price), minPrice, maxPrice);
            }
        };
    }

    public static Specification<Product> findProductByFactoryNameIn(List<String> factoryNames) {
        return new Specification<Product>() {
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get(Product_.FACTORY).in(factoryNames);
            }
        };
    }

    public static Specification<Product> findProductByTarget(List<String> factoryNames) {
       return new Specification<Product>() {
           public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               return root.get(Product_.TARGET).in(factoryNames);
           }
       };
    }

}
