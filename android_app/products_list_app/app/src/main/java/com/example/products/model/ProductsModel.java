package com.example.products.model;

import com.example.products.data.objects.Product;
import com.example.products.data.services.ProductsService;
import com.example.products.views.details.model.ProductDetailsModel;
import com.example.products.views.list.model.ProductsListModel;

import org.greenrobot.eventbus.EventBus;

public class ProductsModel implements ProductsListModel, ProductDetailsModel {

    public static ProductsModel getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final ProductsModel INSTANCE = new ProductsModel();
    }

    private final ProductsService productsService = ProductsService.getInstance();
    private final MappedProducts mappedProducts = new MappedProducts();

    private ProductsModel() {
    }

    public void getAllProducts() {
        productsService.getAll(this::post);
    }

    public void getPaginatedProducts() {
        post(new PaginatedProducts(mappedProducts));
    }

    public void getProduct(long productId) {
        productsService.get(productId, this::post);
    }

    public void addProduct(Product product) {
        productsService.add(product, this::post);
    }

    public void updateProduct(Product product) {
        productsService.update(product, this::post);
    }

    public void deleteProduct(Product product) {
        productsService.delete(product, this::post);
    }

    public void deleteProduct(int position, Product product) {
        productsService.delete(product, data -> post(data.withPosition(position)));
    }


    private void post(Object obj) {
        EventBus.getDefault().post(obj);
    }
}
