package com.vn.shoplaptopp.validation;

import com.vn.shoplaptopp.domain.dto.request.RegisterRequest;
import com.vn.shoplaptopp.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterRequest> {

    private final UserService userService;

    public RegisterValidator(UserService userService){
        this.userService = userService;
    }

    @Override
    public void initialize(RegisterChecked constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {

        //Check password
        if(!registerRequest.getPassWord().equals(registerRequest.getConfirmPassWord())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("PassWord must be same")
                    .addPropertyNode("confirmPassWord")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        //Check email
        if(this.userService.getEmailExist(registerRequest.getEmail())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email is exist")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }
}