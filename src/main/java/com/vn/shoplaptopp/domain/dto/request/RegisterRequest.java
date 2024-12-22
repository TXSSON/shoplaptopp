package com.vn.shoplaptopp.domain.dto.request;

import com.vn.shoplaptopp.validation.RegisterChecked;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RegisterChecked
public class RegisterRequest {
    @NotNull(message = " First name không được để trống")
    @NotBlank (message = "Không hợp lệ")
    private String firstName;
    @NotNull(message = "không được để trống")
    @NotBlank (message = "Không hợp lệ")
    private String lastName;
    @NotNull(message = "không được để trống")
    @NotBlank (message = "Không hợp lệ")
    private String email;
    @NotNull(message = "không được để trống")
    @NotBlank (message = "Không hợp lệ")
    private String passWord;
    @NotNull(message = "không được để trống")
    @NotBlank (message = "Không hợp lệ")
    private String confirmPassWord;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassWord() {
        return confirmPassWord;
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
    }

}
