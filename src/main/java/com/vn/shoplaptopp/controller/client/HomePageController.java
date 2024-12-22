package com.vn.shoplaptopp.controller.client;

import java.util.List;

import com.vn.shoplaptopp.domain.Order;
import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.service.OrderService;
import com.vn.shoplaptopp.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomePageController {

    private final ProductService productService;
    private final OrderService orderService;

    public HomePageController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam(value = "page", required = false) String page) {
        int currentPage = 1;
        if (page != null) {
            try {
                currentPage = Integer.parseInt(page);
            } catch (Exception e) {
                currentPage = 1;
            }
        }
        Page<Product> productPage = this.productService.getAllPageProduct(currentPage - 1, 10);
        List<Product> products = productPage.getContent();

        int totalPages = productPage.getTotalPages();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("currentPage", currentPage);
        return "client/homepage/show";
    }

    @GetMapping("/order-history")
    public String getHistoryOrder(Model model, HttpServletRequest request,  @RequestParam(value = "page", required = false) String page) {
        int currentPage = 1;
        if (page != null) {
            try {
                currentPage = Integer.parseInt(page);
            } catch (Exception e) {
                currentPage = 1;
            }
        }

        HttpSession session = request.getSession();

        Page<Order> orderPage = this.orderService.getAllPageOrders(session,currentPage - 1, 2);
        List<Order> orders = orderPage.getContent();
        int totalPages = orderPage.getTotalPages();
        model.addAttribute("orders", orders);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        return "client/cart/order-history";
    }

}