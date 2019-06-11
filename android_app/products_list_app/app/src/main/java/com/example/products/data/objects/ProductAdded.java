package com.example.products.data.objects;

public class ProductAdded extends ProductsChanged {

    public static ProductAdded empty() {
        return new ProductAdded();
    }

    private ProductAdded() {
    }
}
