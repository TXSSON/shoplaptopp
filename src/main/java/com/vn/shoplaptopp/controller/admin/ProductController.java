package com.vn.shoplaptopp.controller.admin;

import java.util.List;

import com.vn.shoplaptopp.domain.Product;
import com.vn.shoplaptopp.service.ProductService;
import com.vn.shoplaptopp.service.UploadImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;




import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    private final UploadImageService uploadImageService;
    private final ProductService productService;

    public ProductController(UploadImageService uploadImageService, ProductService productService) {
        this.uploadImageService = uploadImageService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        int currentPage = page <= 0 ? 1 : page;
        Page<Product> productPage = this.productService.getAllPageProduct(currentPage - 1, 5);
        List<Product> products = productPage.getContent();
        int totalPages = productPage.getTotalPages();
        if(currentPage > totalPages) {
            currentPage = totalPages;
            productPage = this.productService.getAllPageProduct(currentPage - 1, 5);
            products = productPage.getContent();
        }
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(@ModelAttribute("newProduct") @Valid Product product,
            BindingResult productBindingResult, @RequestParam("fileImage") MultipartFile multipartFile) {
        List<FieldError> errors = productBindingResult.getFieldErrors();
        errors.forEach(error -> System.out.println(">>>>> " + error.getField() + error.getDefaultMessage()));
        if (productBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String finalName = this.uploadImageService.handleSaveUploadImageService(multipartFile, "product");
        product.setImage(finalName);
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(@PathVariable Long id, Model model) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("product") @Valid Product product,
            BindingResult productBindingResult,
            @RequestParam("fileImage") MultipartFile fileImage) {
        if (productBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        if (fileImage != null) {
            String finalName = this.uploadImageService.handleSaveUploadImageService(fileImage, "product");
            product.setImage(finalName);
        }
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product" + product.getId();
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String handleDeleteProduct(@RequestParam Long id) {
        this.productService.handleDeleteProduct(id);
        return "redirect:/admin/product";
    }

}
