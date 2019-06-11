package com.example.products.views.list.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.products.R;
import com.example.products.data.objects.Product;
import com.example.products.model.PaginatedProducts;
import com.example.products.views.list.ProductsDependencies;
import com.example.products.views.list.presenter.ProductsListPresenterInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductsListFragment extends Fragment implements ProductsListView {

    public interface NavigationCallbacks {
        void openProductDetailsForCreate();
        void openProductDetailsForEdit(Product product);
    }


    private final Logger logger = LoggerFactory.getLogger(ProductsListFragment.class);

    private final ProductsListPresenterInterface presenter = ProductsDependencies.getPresenter();
    private final ProductsListAdapter productsListAdapter = new ProductsListAdapter();
    private NavigationCallbacks navigation;

    @BindView(R.id.data_list) RecyclerView productsList;
    @BindView(R.id.add_button) FloatingActionButton addButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_products_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        initViews();
        initListeners();
    }

    private void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    private void initViews() {
        productsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void initListeners() {
        addButton.setOnClickListener(view -> {
            presenter.addItemClicked();
        });
        productsListAdapter.setOnEditItemListener(presenter::editItemClicked);
        productsListAdapter.setOnDeleteItemListener(presenter::deleteItemClicked);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    public void onStop() {
        presenter.onStop();
        productsListAdapter.close();
        super.onStop();
    }

    @Override
    public void setData(PaginatedProducts products) {
        productsListAdapter.setData(products);

        if (productsList.getAdapter() == null) {
            productsList.setAdapter(productsListAdapter);
        }
    }

    public void setNavigationCallbacks(NavigationCallbacks navigation) {
        this.navigation = navigation;
    }

    @Override
    public void openProductDetailsForCreate() {
        if (navigation != null) {
            navigation.openProductDetailsForCreate();
        }
    }

    @Override
    public void openProductDetailsForEdit(Product product) {
        if (navigation != null) {
            navigation.openProductDetailsForEdit(product);
        }
    }
}
