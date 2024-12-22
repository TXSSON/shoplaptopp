package com.vn.shoplaptopp.service;

import com.vn.shoplaptopp.domain.Cart;
import com.vn.shoplaptopp.domain.CartDetail;
import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.repository.CartDetailRepository;
import com.vn.shoplaptopp.repository.CartRepository;
import com.vn.shoplaptopp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailService {

    private final UserRepository userRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;

    public CartDetailService(UserRepository userRepository, CartDetailRepository cartDetailRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
    }

    public void handleDeleteCartDetail(Long cartDetailId, HttpSession session) {

        User user = this.userRepository.findById((Long) session.getAttribute("id")).get();
        Cart cart = this.cartRepository.findByUser(user);
        Optional<CartDetail> optionalCartDetail = this.cartDetailRepository.findById(cartDetailId);

        if (!optionalCartDetail.isPresent()) {
            return;
        }

        CartDetail cartDetail = optionalCartDetail.get();
        this.cartDetailRepository.delete(cartDetail);

        session.setAttribute("sumOfCart", cart.getSum() == 1 ? 0 : cart.getSum() - 1);
        if (cart.getSum() > 1) {
            cart.setSum(cart.getSum() - 1);
            this.cartRepository.save(cart);
        } else {
            this.cartRepository.delete(cart);
        }

    }
    public CartDetail getCartDetailById(Long id) {
        Optional<CartDetail> cartDetail = this.cartDetailRepository.findById(id);
        return cartDetail.orElse(null);
    }
    public void handleUpdateCartDetailBeforeCheckout(Cart cart){
        for (CartDetail cartDetail : cart.getCartDetails()) {
            CartDetail otherCartDetail = this.cartDetailRepository.findById(cartDetail.getId()).orElse(null);
            if (otherCartDetail != null) {
                otherCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(otherCartDetail);
            }
        }
    }
    public List<CartDetail> getCartDetailsByCart(Cart cart) {

        List<CartDetail> cartDetails = this.cartDetailRepository.findByCart(cart);
        return cartDetails == null ? new ArrayList<CartDetail>() : cartDetails;
    }
}
