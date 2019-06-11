package com.example.products.model;

public class MappedProductsUpdated {

    public static MappedProductsUpdated from(int offset, int size) {
        return new MappedProductsUpdated(offset, size);
    }

    private final int offset;
    private final int size;

    private MappedProductsUpdated(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "MappedProductsUpdated{" +
                "offset=" + offset +
                ", size=" + size +
                '}';
    }
}
