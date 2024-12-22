package com.vn.shoplaptopp.repository;

import com.vn.shoplaptopp.domain.Order;
import com.vn.shoplaptopp.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);
}
