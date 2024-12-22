package com.vn.shoplaptopp.controller.client;

import com.vn.shoplaptopp.domain.dto.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute LoginRequest loginRequest) {
        return "redirect:/";
    }
}
