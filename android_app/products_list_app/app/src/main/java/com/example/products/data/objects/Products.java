package com.example.products.data.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {

    public static Products from(List<Product> products) {
        return new Products(products);
    }

    private final List<Product> products;

    private Products(List<Product> products) {
        this.products = Collections.unmodifiableList(new ArrayList<>(products));
    }

    public List<Product> get() {
        return products;
    }
}
