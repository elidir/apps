package com.example.products.views.details.view;

import com.example.products.data.objects.Product;

public interface ProductDetailsView {

    ProductDetailsAction getAction();
    boolean isReconstructed();
    long getProductId();

    String getProductName();
    String getProductAmount();
    String getProductPrice();

    void showInvalidInputError(String errorMessage);

    void setFields(Product product);
    void close();
}
