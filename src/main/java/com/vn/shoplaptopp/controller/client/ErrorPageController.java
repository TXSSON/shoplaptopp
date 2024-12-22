package com.vn.shoplaptopp.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/error/403")
    public String get404Page() {
        return "error/403/show";
    }
}
