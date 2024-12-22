package com.vn.shoplaptopp.controller.admin;

import com.vn.shoplaptopp.service.OrderService;
import com.vn.shoplaptopp.service.ProductService;
import com.vn.shoplaptopp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DashBoardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getDashBoardPage(Model model) {
        model.addAttribute("countUser", this.userService.countAllUsers());
        model.addAttribute("countProduct", this.productService.countAllProducts());
        model.addAttribute("countOrder", this.orderService.countOrders());
        return "admin/dashboard/show";
    }

}
