package com.vn.shoplaptopp.service;

import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.domain.dto.request.RegisterRequest;
import com.vn.shoplaptopp.service.mapper.RegisterMapper;
import org.springframework.stereotype.Service;



@Service
public class RegisterService {

    private final RegisterMapper registerMapper;
    private final UserService userService;

    public RegisterService(RegisterMapper registerMapper, UserService userService) {
        this.registerMapper = registerMapper;
        this.userService = userService;
    }

    public void handleSaveUser(RegisterRequest registerRequest) {
        User user = registerMapper.toUser(registerRequest);
        this.userService.handleSaveUser(user);
    }
}
