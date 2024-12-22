package com.vn.shoplaptopp.service;

import com.vn.shoplaptopp.domain.Order;
import com.vn.shoplaptopp.domain.OrderDetail;
import com.vn.shoplaptopp.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    private OrderDetailRepository orderDetailRepository;
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrder(order);
        return orderDetails == null ? new ArrayList<OrderDetail>() : orderDetails;
    }
}
