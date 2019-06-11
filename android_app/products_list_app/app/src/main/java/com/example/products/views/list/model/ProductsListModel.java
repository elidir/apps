package com.example.products.views.list.model;

import com.example.products.data.objects.Product;

public interface ProductsListModel {
    void getAllProducts();
    void getPaginatedProducts();

    void deleteProduct(Product product);
    void deleteProduct(int position, Product product);
}
