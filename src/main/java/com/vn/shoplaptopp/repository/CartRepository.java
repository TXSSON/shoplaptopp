package com.vn.shoplaptopp.repository;

import com.vn.shoplaptopp.domain.Cart;
import com.vn.shoplaptopp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
}
