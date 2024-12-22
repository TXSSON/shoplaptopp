package com.vn.shoplaptopp.domain.dto.request;

import jakarta.persistence.Entity;

public class LoginRequest {

    private String email;
    private String passWord;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
    }
}
