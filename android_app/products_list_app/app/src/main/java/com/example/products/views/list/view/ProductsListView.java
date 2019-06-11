package com.example.products.views.list.view;

import com.example.products.data.objects.Product;
import com.example.products.model.PaginatedProducts;

public interface ProductsListView {
    void setData(PaginatedProducts products);

    void openProductDetailsForCreate();
    void openProductDetailsForEdit(Product product);
}
