package com.example.products.views.list.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.main.App;
import com.example.products.R;
import com.example.products.data.objects.Product;
import com.example.products.views.details.view.ProductDetailsActivity;
import com.example.products.views.details.view.ProductDetailsFragment;
import com.example.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsListActivity extends AppCompatActivity implements ProductsListFragment.NavigationCallbacks, ProductDetailsFragment.NavigationCallbacks {

    private static final String PRODUCT_DETAILS_FRAGMENT_TAG = "product_details";

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_products_list);

        findViews();
        initViews();
        initListeners();
    }

    private void findViews() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
    }

    private void initListeners() {
        ProductsListFragment listFragment = FragmentUtils.find(getSupportFragmentManager(), R.id.products_list);
        if (listFragment != null) {
            listFragment.setNavigationCallbacks(this);
        }
        ProductDetailsFragment detailsFragment = FragmentUtils.find(getSupportFragmentManager(), PRODUCT_DETAILS_FRAGMENT_TAG);
        if (detailsFragment != null) {
            detailsFragment.setNavigationCallbacks(this);
        }
    }

    @Override
    public void openProductDetailsForCreate() {
        if (App.config().isTablet()) {
            replaceDetailsFragment(ProductDetailsFragment.forCreate());
        } else {
            startActivity(ProductDetailsActivity.getIntentForCreate(this));
        }
    }

    @Override
    public void openProductDetailsForEdit(Product product) {
        if (App.config().isTablet()) {
            replaceDetailsFragment(ProductDetailsFragment.forEdit(product.getId()));
        } else {
            startActivity(ProductDetailsActivity.getIntentForEdit(this, product.getId()));
        }
    }

    private void replaceDetailsFragment(ProductDetailsFragment fragment) {
        fragment.setNavigationCallbacks(this);
        FragmentUtils.replace(getSupportFragmentManager(), R.id.product_details_container, fragment, PRODUCT_DETAILS_FRAGMENT_TAG);
    }

    @Override
    public void closeProductDetails() {
        FragmentUtils.remove(getSupportFragmentManager(), PRODUCT_DETAILS_FRAGMENT_TAG);
    }
}
