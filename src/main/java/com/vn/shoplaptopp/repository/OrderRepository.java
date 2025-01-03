package com.vn.shoplaptopp.repository;


import com.vn.shoplaptopp.domain.Order;
import com.vn.shoplaptopp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser(User user, Pageable pageable);
}