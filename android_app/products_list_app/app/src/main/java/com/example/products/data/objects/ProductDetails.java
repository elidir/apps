package com.example.products.data.objects;

public class ProductDetails {

    public static ProductDetails from(Product product) {
        return new ProductDetails(product);
    }

    private final Product product;

    private ProductDetails(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public boolean hasProduct() {
        return (product != null);
    }
}
