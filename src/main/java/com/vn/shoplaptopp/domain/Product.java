package com.vn.shoplaptopp.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @NotNull(message = "Tên sản phẩm không hợp lệ")
    private String name;
    @NotNull(message = "Giá sản phẩm không hợp lệ")
    @DecimalMin(value = "0.1", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private double price;

    private String image;
    @NotBlank(message = "Detail Description không được để trống")
    @NotNull(message = "Detail Description sản phẩm không hợp lệ")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;
    @NotBlank(message = "Short Detail không được để trống")
    @NotNull(message = "Short Detail sản phẩm không hợp lệ")
    private String shortDetail;
    @NotNull(message = "Số lượng sản phẩm không hợp lệ")
    @Min(value = 1, message = "Giá sản phẩm phải lớn hơn 0")
    private Long quantity;
    private Long sold;
    private String factory;
    private String target;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDetail() {
        return shortDetail;
    }

    public void setShortDetail(String shortDetail) {
        this.shortDetail = shortDetail;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<CartDetail> getCartDetail() {
        return cartDetails;
    }

    public void setCartDetail(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", detailDesc='" + detailDesc + '\'' +
                ", shortDetail='" + shortDetail + '\'' +
                ", quantity=" + quantity +
                ", sold=" + sold +
                ", factory='" + factory + '\'' +
                ", target='" + target + '\'' +
                ", orderDetails=" + orderDetails +
                ", cartDetails=" + cartDetails +
                '}';
    }
}
