package com.example.products.views.details.model;

import com.example.products.data.objects.Product;

public interface ProductDetailsModel {
    void getProduct(long productId);
    void addProduct(Product product);
    void updateProduct(Product product);
}
