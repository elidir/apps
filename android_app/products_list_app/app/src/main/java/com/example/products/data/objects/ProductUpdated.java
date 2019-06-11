package com.example.products.data.objects;

public class ProductUpdated extends ProductsChanged {

    public static ProductUpdated empty() {
        return new ProductUpdated();
    }

    private ProductUpdated() {
    }
}

