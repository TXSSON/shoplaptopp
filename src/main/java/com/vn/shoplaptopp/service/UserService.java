package com.vn.shoplaptopp.service;

import java.util.List;

import com.vn.shoplaptopp.domain.Role;
import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.repository.RoleRepository;
import com.vn.shoplaptopp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<User> getAllPageUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.userRepository.findAll(pageable);
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

    public Boolean getEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Long countAllUsers() {
        return this.userRepository.count();
    }

}
