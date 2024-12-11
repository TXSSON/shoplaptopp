package com.vn.shoplaptopp.controller.client;

import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemPageController {

    private final ProductService productService;

    public ItemPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product-detail/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

}
