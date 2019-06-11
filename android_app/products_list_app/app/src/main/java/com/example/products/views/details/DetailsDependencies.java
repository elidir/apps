package com.example.products.views.details;

import com.example.products.model.ProductsModel;
import com.example.products.views.details.model.ProductDetailsModel;
import com.example.products.views.details.presenter.ProductDetailsPresenter;
import com.example.products.views.details.presenter.ProductDetailsPresenterInterface;

public class DetailsDependencies {

    public static ProductDetailsPresenterInterface getPresenter() {
        return new ProductDetailsPresenter();
    }

    public static ProductDetailsModel getModel() {
        return ProductsModel.getInstance();
    }


    private DetailsDependencies() {
        throw new UnsupportedOperationException();
    }
}
