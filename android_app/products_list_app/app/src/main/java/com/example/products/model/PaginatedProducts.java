package com.example.products.model;

import com.example.products.data.objects.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PaginatedProducts {

    public interface UpdatesListener {
        void onProductsChanged(int offset, int size);
        void onProductDeleted(int position);
    }

    private final MappedProductsListener mappedProductsListener = new MappedProductsListener();
    private final MappedProducts mappedProducts;
    private UpdatesListener listener;


    PaginatedProducts(MappedProducts mappedProducts) {
        this.mappedProducts = mappedProducts;
        mappedProductsListener.register();
    }


    public Product get(int index) {
        return mappedProducts.get(index);
    }

    public int size() {
        return mappedProducts.size();
    }


    public void setListener(UpdatesListener listener) {
        this.listener = listener;
    }


    public void close() {
        mappedProductsListener.unregister();
    }


    private class MappedProductsListener {

        void register() {
            EventBus.getDefault().register(this);
        }

        void unregister() {
            EventBus.getDefault().unregister(this);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onProductsUpdated(MappedProductsUpdated updated) {
            if (listener != null) {
                listener.onProductsChanged(updated.getOffset(), updated.getSize());
            }
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onProductDeleted(MappedProductDeleted deleted) {
            if (listener != null) {
                listener.onProductDeleted(deleted.getPosition());
            }
        }
    }
}
