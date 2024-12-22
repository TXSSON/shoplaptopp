package com.vn.shoplaptopp.controller.admin;

import java.util.List;

import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.service.UploadImageService;
import com.vn.shoplaptopp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;




import jakarta.validation.Valid;

@Controller
@SuppressWarnings("unused")
public class UserController {

    private final UserService userService;
    private final UploadImageService uploadImageService;
    private final PasswordEncoder passwordEncoder;

    private UserController(UserService userService,
            UploadImageService uploadImageService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadImageService = uploadImageService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUsersPage(Model model,@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        int currentPage = page <= 0 ? 1 : page;
        Page<User> userPage = this.userService.getAllPageUsers(currentPage - 1, 3);
        List<User> users = userPage.getContent();
        int totalPages = userPage.getTotalPages();
        if(currentPage > totalPages) {
            currentPage = totalPages;
            userPage = this.userService.getAllPageUsers(currentPage - 1, 3);
            users = userPage.getContent();
        }
        model.addAttribute("users", users);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("currentPage", currentPage);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update/")
    public String handleUpdateUserPage(Model model, @ModelAttribute("user") @Valid User user,
            BindingResult userBindingResult,
            @RequestParam MultipartFile fileImage) {

        List<FieldError> errors = userBindingResult.getFieldErrors();
        errors.forEach(error -> {
            System.out.println(">>>>>> " + error.getField() + " " + error.getDefaultMessage());
        });
        if (userBindingResult.hasFieldErrors("name")) {
            return "admin/user/update";
        }
        if (fileImage != null) {
            String pathAvatar = this.uploadImageService.handleSaveUploadImageService(fileImage, "avatar");
            user.setAvatar(pathAvatar);
        }
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));
        User currentUser = this.userService.handleSaveUser(user);
        return "redirect:/admin/user/" + currentUser.getId();
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String handleCreateUser(Model model, @ModelAttribute("newUser") @Valid User newUser,
            BindingResult newUserBindingResult,
            @RequestParam MultipartFile fileImage) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>> " + error.getField() + " - " + error.getDefaultMessage());
        }
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        String finalName = this.uploadImageService.handleSaveUploadImageService(fileImage, "avatar");
        newUser.setAvatar(finalName);
        String hashPassWord = this.passwordEncoder.encode(newUser.getPassWord());
        newUser.setPassWord(hashPassWord);
        newUser.setRole(this.userService.getRoleByName(newUser.getRole().getName()));
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getMethodName(Model model, @PathVariable Long id) {
        User user = new User();
        user.setId(id);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String handleDeleteUser(Long id) {
        this.userService.handleDeleteUser(id);
        return "redirect:/admin/user";
    }

}
