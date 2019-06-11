package com.example.products.views.list;

import com.example.products.model.ProductsModel;
import com.example.products.views.list.model.ProductsListModel;
import com.example.products.views.list.presenter.ProductsListPresenter;
import com.example.products.views.list.presenter.ProductsListPresenterInterface;

public class ProductsDependencies {

    public static ProductsListPresenterInterface getPresenter() {
        return new ProductsListPresenter();
    }

    public static ProductsListModel getModel() {
        return ProductsModel.getInstance();
    }


    private ProductsDependencies() {
        throw new UnsupportedOperationException();
    }
}
