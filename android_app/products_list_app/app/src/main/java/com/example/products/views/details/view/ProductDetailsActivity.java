package com.example.products.views.details.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.products.R;
import com.example.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductDetailsActivity extends AppCompatActivity {

    private static final String PRODUCT_DETAILS_FRAGMENT_BUNDLE_KEY = "fragment_bundle_key";
    private static final String DETAILS_FRAGMENT_TAG = "details_fragment";


    public static Intent getIntentForCreate(Context context) {
        Bundle bundle = ProductDetailsFragment.bundleForCreate();
        return getIntent(context, bundle);
    }

    public static Intent getIntentForEdit(Context context, long productId) {
        Bundle bundle = ProductDetailsFragment.bundleForEdit(productId);
        return getIntent(context, bundle);
    }

    private static Intent getIntent(Context context, Bundle productDetailsFragmentBundle) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(PRODUCT_DETAILS_FRAGMENT_BUNDLE_KEY, productDetailsFragmentBundle);
        return intent;
    }


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.product_details_container) View fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_product_details);

        findViews();
        initViews();
        initListeners();
    }

    private void findViews() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.product_details);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        ProductDetailsFragment fragment = FragmentUtils.setRoot(getSupportFragmentManager(), R.id.product_details_container, DETAILS_FRAGMENT_TAG, () -> {
            Bundle bundle = getIntent().getBundleExtra(PRODUCT_DETAILS_FRAGMENT_BUNDLE_KEY);
            return ProductDetailsFragment.from(bundle);
        });
        fragment.setNavigationCallbacks(this::finish);
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
