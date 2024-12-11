package com.vn.shoplaptopp.controller.client;


import com.vn.shoplaptopp.domain.dto.request.RegisterRequest;
import com.vn.shoplaptopp.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




import jakarta.validation.Valid;

@Controller
public class RegisterPageController {

    private final RegisterService registerService;

    public RegisterPageController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("register", new RegisterRequest());
        return "/client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(Model model, @ModelAttribute("register") @Valid RegisterRequest registerRequest,
            BindingResult registerDTOBindingResult) {

        if (registerDTOBindingResult.hasErrors()) {
            return "/client/auth/register";
        }

        this.registerService.handleSaveUser(registerRequest);
        return "/client/homepage/show";
    }
}
