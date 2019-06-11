package com.example.products.views.details.presenter;

import com.example.products.views.details.view.ProductDetailsView;

public interface ProductDetailsPresenterInterface {
    void onStart(ProductDetailsView view);
    void onStop();

    void onCancel();
    void onCreateProduct();
    void onSaveProduct();
}
