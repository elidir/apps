package com.example.products.data.services;

import com.example.products.data.api.ProductsStorageApi;
import com.example.products.data.objects.Product;
import com.example.products.data.objects.ProductAdded;
import com.example.products.data.objects.ProductDeleted;
import com.example.products.data.objects.ProductDetails;
import com.example.products.data.objects.ProductUpdated;
import com.example.products.data.objects.Products;
import com.example.products.data.objects.ProductsChanged;
import com.example.products.data.objects.ProductsPage;
import com.example.utils.callbacks.OnSuccess;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;

public class ProductsService {

    public static ProductsService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final ProductsService INSTANCE = new ProductsService();
    }

    private final ProductsStorageApi productsStorageApi = new ProductsStorageApi();

    private ProductsService() {
    }

    public void getAll(OnSuccess<Products> success) {
        Single.fromCallable(productsStorageApi::getAll)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success::onSuccess);
    }

    public void get(int offset, int size, OnSuccess<ProductsPage> success) {
        Single.fromCallable(() -> productsStorageApi.get(offset, size))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success::onSuccess);
    }

    public void get(long productId, OnSuccess<ProductDetails> success) {
        Single.just(productId)
                .map(productsStorageApi::get)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success::onSuccess);
    }

    public void add(Product product, OnSuccess<ProductsChanged> success) {
        Single.just(product)
                .map(productsStorageApi::add)
                .map(added -> throwOnError(added, "Can't insert either null object or object with id"))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(added -> success.onSuccess(ProductAdded.empty()));
    }

    public void update(Product product, OnSuccess<ProductUpdated> success) {
        Single.just(product)
                .map(productsStorageApi::update)
                .map(updated -> throwOnError(updated, "Can't update either null object or object without id"))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updated -> success.onSuccess(ProductUpdated.empty()));
    }

    public void delete(Product product, OnSuccess<ProductDeleted> success) {
        Single.just(product)
                .map(productsStorageApi::delete)
                .map(deleted -> throwOnError(deleted, "Can't delete object without id"))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleted -> success.onSuccess(ProductDeleted.from(product.getId())));
    }


    private boolean throwOnError(boolean success, String errorMessage) {
        if (success) {
            return true;
        } else {
            throw Exceptions.propagate(new IOException(errorMessage));
        }
    }
}
