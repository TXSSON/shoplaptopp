package com.vn.shoplaptopp.repository;

import com.vn.shoplaptopp.domain.Cart;
import com.vn.shoplaptopp.domain.CartDetail;
import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    CartDetail findByCartAndProduct(Cart cart, Product product);
    List<CartDetail> findByCart(Cart cart);
}
