package com.vn.shoplaptopp.controller.admin;

import com.vn.shoplaptopp.domain.Order;
import com.vn.shoplaptopp.domain.OrderDetail;
import com.vn.shoplaptopp.service.OrderDetailService;
import com.vn.shoplaptopp.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public OrderController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }


    @GetMapping("/admin/order")
    public String getOrderPage(Model model, HttpServletRequest request,  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        int currentPage = page <= 0 ? 1 : page;
        HttpSession session = request.getSession();

        Page<Order> orderPage = this.orderService.getAllPageOrders(session,currentPage - 1, 2);
        List<Order> orders = orderPage.getContent();
        int totalPages = orderPage.getTotalPages();
        if(currentPage > totalPages) {
            currentPage = totalPages;
            orderPage = this.orderService.getAllPageOrders(session,currentPage - 1, 2);
            orders = orderPage.getContent();
        }
        model.addAttribute("orders", orders);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("currentPage", currentPage);
        return "admin/order/show";
    }
    @GetMapping("/admin/order/{id}")
    public String getViewDetailPage(@PathVariable("id") Long id, Model model) {

        List<OrderDetail> orderDetails = this.orderDetailService.getOrderDetailByOrderId(id);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderId", id);
        return "admin/order/detail";
    }
    @GetMapping("/admin/order-update/{id}")
    public String handleUpdateOrder(Model model, @PathVariable("id") Long id) {
        Order order = this.orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "admin/order/update";
    }
    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("order") Order order) {
        this.orderService.handleUpdateOrder(order);
        return "redirect:/admin/order";
    }
    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(@PathVariable("id") Long id, Model model) {
        Order order = this.orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("orderId", id);
        return "admin/order/delete";

    }

    @PostMapping("/admin/order/delete/{id}")
    public String handleDeleteOrder(@PathVariable("id") Long id, Model model) {
        this.orderService.handleDeleteOrderById(id);
        return "redirect:/admin/order";
    }

}
