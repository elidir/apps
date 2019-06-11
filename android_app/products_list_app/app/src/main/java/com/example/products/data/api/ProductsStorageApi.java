package com.example.products.data.api;

import android.support.annotation.Nullable;

import com.example.main.App;
import com.example.products.data.objects.Product;
import com.example.products.data.objects.ProductDao;
import com.example.products.data.objects.ProductDetails;
import com.example.products.data.objects.Products;
import com.example.products.data.objects.ProductsPage;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class ProductsStorageApi {

    private final App app = App.getInstance();

    public Products getAll() {
        return Products.from(getSortedProductQueryBuilder().list());
    }

    public ProductsPage get(int offset, int size) {
        List<Product> products = getSortedProductQueryBuilder()
                .offset(offset)
                .limit(size)
                .list();
        return ProductsPage.from(products, offset, size);
    }

    private QueryBuilder<Product> getSortedProductQueryBuilder() {
        return app.getDatabaseSession()
                .getProductDao()
                .queryBuilder()
                .orderDesc(ProductDao.Properties.Id);
    }


    public ProductDetails get(long productId) {
        Product product = app.getDatabaseSession().getProductDao().load(productId);
        return ProductDetails.from(product);
    }

    /**
     * @return {@code true} if successfully inserted, {@code false} otherwise
     */
    public boolean add(Product product) {
        boolean valid = (product != null && product.getId() == null);
        if (valid) {
            app.getDatabaseSession().getProductDao().insert(product);
        }
        return valid;
    }

    /**
     * @return {@code true} if successfully updated, {@code false} otherwise
     */
    public boolean update(Product product) {
        boolean containsId = (containsId(product));
        if (containsId) {
            app.getDatabaseSession().getProductDao().update(product);
        }
        return containsId;
    }

    /**
     * @return {@code true} if successfully deleted, {@code false} otherwise
     */
    public boolean delete(Product product) {
        boolean containsId = containsId(product);
        if (containsId) {
            app.getDatabaseSession().getProductDao().deleteByKey(product.getId());
        }
        return containsId;
    }

    private boolean containsId(@Nullable Product product) {
        return (product != null && product.getId() != null);
    }
}
