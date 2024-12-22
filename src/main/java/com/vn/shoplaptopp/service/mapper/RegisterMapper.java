package com.vn.shoplaptopp.service.Mapper;


import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.domain.dto.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class RegisterMapper {

    private final PasswordEncoder passwordEncoder;

    public RegisterMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setFullName(registerRequest.getFirstName() + registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        String hashPassWord = this.passwordEncoder.encode(registerRequest.getPassWord());
        user.setPassWord(hashPassWord);
        return user;
    }
}
