package com.vn.shoplaptopp.service;

import java.util.List;

import com.vn.shoplaptopp.domain.Role;
import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.repository.RoleRepository;
import com.vn.shoplaptopp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String getHomePage() {
        return new String("Xin chào đến với UserService");
    }

    public User handleSaveUser(User newUser) {
        if (newUser.getId() == null) {
            newUser.setFullName(newUser.getFullName().trim());
            return this.userRepository.save(newUser);
        }
        User currentUser = this.userRepository.findById(newUser.getId()).get();
        // Update user
        currentUser.setPhone(newUser.getPhone());
        currentUser.setFullName(newUser.getFullName());
        currentUser.setAddress(newUser.getAddress());
        currentUser.setRole(newUser.getRole());
        currentUser.setAvatar(newUser.getAvatar());
        return this.userRepository.save(currentUser);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).get();
    }

    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

}
