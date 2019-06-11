package com.example.products.views.details.presenter;

import android.text.TextUtils;

import com.example.main.App;
import com.example.products.R;
import com.example.utils.MathUtils;
import com.google.common.base.Optional;

import java.util.Scanner;

class ProductValidator {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 10000;

    private static final double MIN_PRICE = 0;
    private static final double MAX_PRICE = 100000;
    private static final double NO_PRICE = MIN_PRICE - 1;


    static String parseName(String nameStr) throws ProductValidationException {
        assertNonEmpty(nameStr, ErrorMessages.nameError());
        return nameStr.trim();
    }

    static long parseAmount(String amountStr) throws ProductValidationException {
        assertNonEmpty(amountStr, ErrorMessages.amountError());
        if (!TextUtils.isDigitsOnly(amountStr)) {
            onInvalidInput(ErrorMessages.amountError());
        }
        long amount = Long.parseLong(amountStr);
        if (!MathUtils.inBounds(amount, MIN_AMOUNT, MAX_AMOUNT)) {
            onInvalidInput(ErrorMessages.amountError());
        }
        return amount;
    }

    static double parsePrice(String priceStr) throws ProductValidationException {
        assertNonEmpty(priceStr, ErrorMessages.priceError());
        double price = parseDouble(priceStr).or(NO_PRICE);
        if (!MathUtils.inBounds(price, MIN_PRICE, MAX_PRICE)) {
            onInvalidInput(ErrorMessages.priceError());
        }
        return price;
    }


    private static void assertNonEmpty(String str, String errorMessage) throws ProductValidationException {
        if (TextUtils.isEmpty(str) || str.trim().isEmpty()) {
            onInvalidInput(errorMessage);
        }
    }

    private static void onInvalidInput(String errorMessage) throws ProductValidationException {
        throw new ProductValidationException(errorMessage);
    }


    private static Optional<Double> parseDouble(String str) {
        Scanner scanner = new Scanner(str);
        try {
            return scanner.hasNextDouble()
                    ? Optional.of(scanner.nextDouble())
                    : Optional.absent();
        } finally {
            scanner.close();
        }
    }


    private ProductValidator() {
        throw new UnsupportedOperationException();
    }

    private static class ErrorMessages {

        static String nameError() {
            return App.getInstance().getString(R.string.product_validation_empty_name_error);
        }

        static String amountError() {
            return App.getInstance().getString(R.string.product_validation_amount_error, MIN_AMOUNT, MAX_AMOUNT);
        }

        static String priceError() {
            return App.getInstance().getString(R.string.product_validation_price_error, MIN_PRICE, MAX_PRICE);
        }
    }
}
