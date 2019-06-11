package com.example.products.views.details.presenter;

import com.example.products.data.objects.Product;
import com.example.products.data.objects.ProductDeleted;
import com.example.products.data.objects.ProductDetails;
import com.example.products.views.details.DetailsDependencies;
import com.example.products.views.details.model.ProductDetailsModel;
import com.example.products.views.details.view.ProductDetailsAction;
import com.example.products.views.details.view.ProductDetailsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ProductDetailsPresenter implements ProductDetailsPresenterInterface {

    private final ProductDetailsModel model = DetailsDependencies.getModel();
    private ProductDetailsView view;

    @Override
    public void onStart(ProductDetailsView view) {
        this.view = view;
        EventBus.getDefault().register(this);

        if (view.getAction() == ProductDetailsAction.UPDATE && !view.isReconstructed()) {
            model.getProduct(view.getProductId());
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCancel() {
        view.close();
    }

    @Override
    public void onCreateProduct() {
        try {
            Product product = validateProduct();
            model.addProduct(product);
            view.close();
        } catch (ProductValidationException e) {
            view.showInvalidInputError(e.getMessage());
        }
    }

    @Override
    public void onSaveProduct() {
        try {
            Product product = validateProduct();
            product.setId(view.getProductId());
            model.updateProduct(product);
            view.close();
        } catch (ProductValidationException e) {
            view.showInvalidInputError(e.getMessage());
        }
    }

    private Product validateProduct() throws ProductValidationException {
        String name = ProductValidator.parseName(view.getProductName());
        long amount = ProductValidator.parseAmount(view.getProductAmount());
        double price = ProductValidator.parsePrice(view.getProductPrice());
        return Product.from(name, amount, price);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductDetails(ProductDetails details) {
        if (details.hasProduct()) {
            view.setFields(details.getProduct());
        } else {
            view.close();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductDeleted(ProductDeleted deleted) {
        if (deleted.getProductId() == view.getProductId()) {
            view.close();
        }
    }
}
