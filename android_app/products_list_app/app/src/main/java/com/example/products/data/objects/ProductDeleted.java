package com.example.products.data.objects;

public class ProductDeleted extends ProductsChanged {

    private static final int NO_POSITION = Integer.MIN_VALUE;


    public static ProductDeleted from(long productId) {
        return new ProductDeleted(productId, NO_POSITION);
    }

    public static ProductDeleted from(long productId, int position) {
        return new ProductDeleted(productId, position);
    }


    private final long productId;
    private final int position;

    private ProductDeleted(long productId, int position) {
        this.productId = productId;
        this.position = position;
    }

    public long getProductId() {
        return productId;
    }

    public ProductDeleted withPosition(int position) {
        return from(getProductId(), position);
    }

    public int getPosition() {
        return position;
    }

    public boolean hasPosition() {
        return (getPosition() != NO_POSITION);
    }

    @Override
    public String toString() {
        return "ProductDeleted{" +
                "productId=" + productId +
                ", position=" + position +
                '}';
    }
}
