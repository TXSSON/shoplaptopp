package com.vn.shoplaptopp.controller.client;

import com.vn.shoplaptopp.domain.Cart;
import com.vn.shoplaptopp.domain.CartDetail;
import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.domain.dto.request.ProductCriteriaRequest;
import com.vn.shoplaptopp.domain.dto.request.ReceiverRequest;
import com.vn.shoplaptopp.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ItemPageController {

    private final ProductService productService;
    private final CartService cartService;
    private final CartDetailService cartDetailService;
    private final OrderService orderService;
    private final UserService userService;


    public ItemPageController(ProductService productService, CartService cartService, CartDetailService cartDetailService, OrderService orderService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/product-detail/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

    @PostMapping("/add_to_cart/{id}")
    public String handleAddToCart(@PathVariable Long id, HttpServletRequest request, @RequestParam(value = "quantity", required = false) Long quantity
    ) {

        this.productService.handleAddProductToCart(id, request, quantity == null ? 1 : quantity);

        return quantity == null ? "redirect:/" : "redirect:/product-detail/" + id;

    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = new User();
        user.setId((Long) session.getAttribute("id"));

        Cart cart = this.cartService.getCartByUser(user);
        double totalPrice = 0D;
        for (CartDetail cartDetail : cart.getCartDetails()) {
            totalPrice += cartDetail.getQuantity() * cartDetail.getPrice();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetails", cart.getCartDetails());
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @PostMapping("/cart/delete/{id}")
    private String handleDeleteCartDetail(@PathVariable Long id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = new User();
        user.setId((Long) session.getAttribute("id"));
        this.cartDetailService.handleDeleteCartDetail(id, session);

        Cart cart = this.cartService.getCartByUser(user);
        double totalPrice = 0D;
        for (CartDetail cartDetail : cart.getCartDetails()) {
            totalPrice += cartDetail.getQuantity() * cartDetail.getPrice();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "redirect: /cart";
    }

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = new User();
        user.setId((Long) session.getAttribute("id"));

        Cart cart = this.cartService.getCartByUser(user);


        double totalPrice = 0D;
        List<CartDetail> cartDetails = this.cartDetailService.getCartDetailsByCart(cart);
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getQuantity() * cartDetail.getPrice();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("receiverRequest", new ReceiverRequest());
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String handleUpdateCartDetailBeforeCheckout(@ModelAttribute("cart") Cart cart) {
        if (!cart.getCartDetails().isEmpty()) {
            this.cartDetailService.handleUpdateCartDetailBeforeCheckout(cart);
        }
        return "redirect: /checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(@ModelAttribute("receiverRequest") ReceiverRequest receiverRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.orderService.handleSaveOrder(receiverRequest, session);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanksPage() {
        return "client/cart/thank";
    }

    @GetMapping("/products")
    public String getProductsPage(Model model,ProductCriteriaRequest productCriteriaRequest, HttpServletRequest httpServletRequest) {
        int currentPage = 1;

        if (!productCriteriaRequest.getPage().equals("")) {
            try {
                currentPage = Integer.parseInt(productCriteriaRequest.getPage());
            } catch (NumberFormatException e) {
                model.addAttribute("products", new ArrayList<Product>());
                return "client/product/show";
            }
        }
        if (currentPage < 1) {
            model.addAttribute("products", new ArrayList<Product>());
            return "client/product/show";
        }

        Page<Product> productPage = this.productService.getAllProductsWithProductCriteria(productCriteriaRequest, currentPage - 1, 3);
        if(productPage == null) {
            productPage = this.productService.getAllPageProduct(currentPage - 1, 3);
        }
        List<Product> products = productPage.getContent();
        int totalPages = productPage.getTotalPages();
        if (currentPage > totalPages) {
            model.addAttribute("products", new ArrayList<Product>());
            return "client/product/show";
        }
        String queryString = httpServletRequest.getQueryString();
        if (queryString != null && !queryString.equals("")) {
            queryString = queryString.replace("page=" + currentPage, "");
        }
        model.addAttribute("products", products);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("queryString", queryString);
        return "client/product/show";
    }
}
