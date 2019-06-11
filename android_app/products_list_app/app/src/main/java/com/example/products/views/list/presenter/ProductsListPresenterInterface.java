package com.example.products.views.list.presenter;

import com.example.products.data.objects.Product;
import com.example.products.views.list.view.ProductsListView;

public interface ProductsListPresenterInterface {

    void onStart(ProductsListView view);
    void onStop();

    void addItemClicked();

    void editItemClicked(int position, Product product);
    void deleteItemClicked(int position, Product product);
}
