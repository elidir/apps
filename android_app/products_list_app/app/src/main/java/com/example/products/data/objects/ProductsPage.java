package com.example.products.data.objects;

import java.util.List;

public class ProductsPage {

    public static ProductsPage from(List<Product> products, int offset, int size) {
        Products from = Products.from(products);
        return new ProductsPage(from, offset, size);
    }


    private final Products products;
    private final int offset;
    private final int size;


    private ProductsPage(Products products, int offset, int size) {
        this.products = products;
        this.offset = offset;
        this.size = size;
    }

    public List<Product> get() {
        return products.get();
    }

    public Product get(int index) {
        return get().get(index);
    }

    public int offset() {
        return offset;
    }

    public int size() {
        return size;
    }

    public int loadedSize() {
        return get().size();
    }
}
