package com.example.products.views.list.presenter;

import com.example.products.data.objects.Product;
import com.example.products.model.PaginatedProducts;
import com.example.products.views.list.ProductsDependencies;
import com.example.products.views.list.model.ProductsListModel;
import com.example.products.views.list.view.ProductsListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ProductsListPresenter implements ProductsListPresenterInterface {

    private final ProductsListModel model = ProductsDependencies.getModel();

    private ProductsListView view;

    @Override
    public void onStart(ProductsListView view) {
        this.view = view;
        EventBus.getDefault().register(this);

        model.getPaginatedProducts();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void addItemClicked() {
        view.openProductDetailsForCreate();
    }

    @Override
    public void editItemClicked(int position, Product product) {
        view.openProductDetailsForEdit(product);
    }

    @Override
    public void deleteItemClicked(int position, Product product) {
        model.deleteProduct(position, product);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductsLoaded(PaginatedProducts products) {
        view.setData(products);
    }

}
