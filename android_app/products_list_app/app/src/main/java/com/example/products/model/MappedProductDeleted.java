package com.example.products.model;

import com.example.products.data.objects.ProductDeleted;

public class MappedProductDeleted {

    public static MappedProductDeleted from(ProductDeleted deleted) {
        return new MappedProductDeleted(deleted);
    }

    private final ProductDeleted deletedProduct;

    private MappedProductDeleted(ProductDeleted deletedProduct) {
        this.deletedProduct = deletedProduct;
    }

    public int getPosition() {
        return deletedProduct.getPosition();
    }

    public boolean hasPosition() {
        return deletedProduct.hasPosition();
    }
}
