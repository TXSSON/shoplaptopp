package com.vn.shoplaptopp.service;

import com.vn.shoplaptopp.domain.Cart;
import com.vn.shoplaptopp.domain.CartDetail;
import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.repository.CartRepository;
import com.vn.shoplaptopp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart getCartByUser(User user) {
        Cart cart = this.cartRepository.findByUser(user);
        if(cart == null) {
            cart = new Cart();
            cart.setCartDetails( new ArrayList<CartDetail>());
        }
        return cart;
    }

}
