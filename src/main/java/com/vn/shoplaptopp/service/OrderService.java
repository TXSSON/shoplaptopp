package com.vn.shoplaptopp.service;


import com.vn.shoplaptopp.domain.*;
import com.vn.shoplaptopp.domain.dto.request.ReceiverRequest;
import com.vn.shoplaptopp.repository.CartDetailRepository;
import com.vn.shoplaptopp.repository.CartRepository;
import com.vn.shoplaptopp.repository.OrderDetailRepository;
import com.vn.shoplaptopp.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(CartRepository cartRepository, CartDetailRepository cartDetailRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public void handleSaveOrder(ReceiverRequest receiverRequest, HttpSession session) {
        User user = new User();
        user.setId((Long) session.getAttribute("id"));
        Order order = new Order();

        Cart cart = this.cartRepository.findByUser(user);
        List<CartDetail> cartDetails = this.cartDetailRepository.findByCart(cart);

        if (cartDetails == null) {
            return;
        }
        Double totalPrice = 0.0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order.setReceiverName(receiverRequest.getReceiverName());
        order.setReceiverPhone(receiverRequest.getReceiverPhone());
        order.setReceiverAddress(receiverRequest.getReceiverAddress());
        order.setStatus("PENDING");
        this.orderRepository.save(order);

        for (CartDetail cartDetail : cartDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartDetail.getProduct());
            this.orderDetailRepository.save(orderDetail);
            this.cartDetailRepository.delete(cartDetail);
        }
        this.cartRepository.delete(cart);
        session.setAttribute("sumOfCart", 0);
    }

    public Page<Order> getAllPageOrders(HttpSession session, int page, int size) {
        User user = new User();
        user.setId((Long) session.getAttribute("id"));
        Pageable pageable = PageRequest.of(page, size);
        return this.orderRepository.findByUser(user, pageable);
    }

    public Order getOrderById(Long id) {
        Order order = this.orderRepository.findById(id).orElse(null);
        return order == null ? new Order() : order;
    }

    public void handleUpdateOrder(Order order) {
        Order realOrder = this.orderRepository.findById(order.getId()).orElse(null);
        if (realOrder != null) {
            realOrder.setStatus(order.getStatus());
            this.orderRepository.save(realOrder);
        }
    }

    public void handleDeleteOrderById(Long id) {
        Order order = new Order();
        order.setId(id);
        List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrder(order);
        if (orderDetails == null) {
            orderDetails = new ArrayList<OrderDetail>();
        }
        for (OrderDetail orderDetail : orderDetails) {
            this.orderDetailRepository.delete(orderDetail);
        }
        this.orderRepository.delete(order);
    }
    public Long countOrders() {
        return this.orderRepository.count();
    }
}
