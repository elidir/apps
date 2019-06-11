package com.example.products.views.details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.products.R;
import com.example.products.data.objects.Product;
import com.example.products.views.details.DetailsDependencies;
import com.example.products.views.details.presenter.ProductDetailsPresenterInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsFragment extends Fragment implements ProductDetailsView {

    public interface NavigationCallbacks {
        void closeProductDetails();
    }

    private static final String PRODUCT_ID_KEY = "id";
    private static final String PRODUCT_ACTION_KEY = "action";

    public static ProductDetailsFragment from(Bundle bundle) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProductDetailsFragment forCreate() {
        return from(bundleForCreate());
    }

    public static ProductDetailsFragment forEdit(long productId) {
        return from(bundleForEdit(productId));
    }

    public static Bundle bundleForCreate() {
        Bundle bundle = new Bundle();
        bundle.putString(PRODUCT_ACTION_KEY, ProductDetailsAction.CREATE.name());
        return bundle;
    }

    public static Bundle bundleForEdit(long productId) {
        Bundle bundle = new Bundle();
        bundle.putString(PRODUCT_ACTION_KEY, ProductDetailsAction.UPDATE.name());
        bundle.putLong(PRODUCT_ID_KEY, productId);
        return bundle;
    }


    private final ProductDetailsPresenterInterface presenter = DetailsDependencies.getPresenter();
    private NavigationCallbacks navigation;
    private boolean isReconstructed;


    @BindView(R.id.product_title) TextView titleView;
    @BindView(R.id.product_amount) TextView amountView;
    @BindView(R.id.product_price) TextView priceView;

    @BindView(R.id.cancel_button) Button cancelButton;
    @BindView(R.id.create_button) Button createButton;
    @BindView(R.id.save_button) Button saveButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_product_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isReconstructed = (savedInstanceState != null);

        findViews(view);
        initViews();
        initListeners();
    }

    private void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    private void initViews() {
        switch (getAction()) {
            case CREATE:
                saveButton.setVisibility(View.GONE);
                break;
            case UPDATE:
                createButton.setVisibility(View.GONE);
                break;
        }
    }

    private void initListeners() {
        cancelButton.setOnClickListener(v -> presenter.onCancel());
        createButton.setOnClickListener(v -> presenter.onCreateProduct());
        saveButton.setOnClickListener(v -> presenter.onSaveProduct());
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }


    @Override
    public ProductDetailsAction getAction() {
        return ProductDetailsAction.valueOf(getArguments().getString(PRODUCT_ACTION_KEY));
    }

    @Override
    public boolean isReconstructed() {
        return isReconstructed;
    }

    @Override
    public long getProductId() {
        return getArguments().getLong(PRODUCT_ID_KEY);
    }

    @Override
    public String getProductName() {
        return titleView.getText().toString();
    }

    @Override
    public String getProductAmount() {
        return amountView.getText().toString();
    }

    @Override
    public String getProductPrice() {
        return priceView.getText().toString();
    }

    @Override
    public void showInvalidInputError(String errorMessage) {
        View rootView = getView();
        if (rootView != null) {
            Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setFields(Product product) {
        titleView.setText(product.getName());
        amountView.setText(String.valueOf(product.getAmount()));
        priceView.setText(String.valueOf(product.getPrice()));
    }

    public void setNavigationCallbacks(NavigationCallbacks navigation) {
        this.navigation = navigation;
    }

    @Override
    public void close() {
        if (navigation != null) {
            navigation.closeProductDetails();
        }
    }
}
