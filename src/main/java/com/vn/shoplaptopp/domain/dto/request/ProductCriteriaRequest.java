package com.vn.shoplaptopp.domain.dto.request;

import java.util.List;
import java.util.Optional;

public class ProductCriteriaRequest {
    private String page;
    private List<String> factory;
    private List<String> target;
    private List<String> price;
    private String sort;

    public String getPage() {
        return page == null ? "" : page;
    }

    public void setPage(String page) {
        this.page = page ;
    }

    public List<String> getFactory() {
        return factory;
    }

    public void setFactory(List<String> factory) {
        this.factory = factory;
    }

    public List<String> getTarget() {
        return target;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
