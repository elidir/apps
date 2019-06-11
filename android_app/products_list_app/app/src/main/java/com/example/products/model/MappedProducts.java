package com.example.products.model;

import com.example.products.data.objects.Product;
import com.example.products.data.objects.ProductAdded;
import com.example.products.data.objects.ProductDeleted;
import com.example.products.data.objects.ProductUpdated;
import com.example.products.data.objects.ProductsPage;
import com.example.products.data.services.ProductsService;
import com.example.utils.MathUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class MappedProducts {

    private static final int FIRST_PAGE_SIZE = 100;
    private static final int PAGE_SIZE = 50;
    private static final int MARGIN_SIZE = 50;


    private final Logger logger = LoggerFactory.getLogger(MappedProducts.class);

    private final LowLevel mappedProducts = new LowLevel();
    private int maxRequestedIndex;


    MappedProducts() {
        loadFirstPage();
        EventBus.getDefault().register(this);
    }


    public Product get(int index) {
        maxRequestedIndex = Math.max(maxRequestedIndex, index);
        loadNextPageIfRequired();
        return mappedProducts.get(index);
    }

    public int size() {
        return mappedProducts.size();
    }


    private void loadFirstPage() {
        mappedProducts.load(0, FIRST_PAGE_SIZE);
    }

    private void loadNextPage() {
        mappedProducts.load(mappedProducts.size(), PAGE_SIZE);
    }

    private void loadNextPageIfRequired() {
        if (maxRequestedIndex + MARGIN_SIZE > mappedProducts.size()) {
            loadNextPage();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductAdded(ProductAdded added) {
        mappedProducts.reload();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductUpdated(ProductUpdated updated) {
        mappedProducts.reload();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductDeleted(ProductDeleted deleted) {
        mappedProducts.delete(deleted.getPosition());
        EventBus.getDefault().post(MappedProductDeleted.from(deleted));
    }

    private void onLoadingComplete(ProductsPage page) {
        loadNextPageIfRequired();
        EventBus.getDefault().post(MappedProductsUpdated.from(page.offset(), page.size()));
    }

    public void close() {
        EventBus.getDefault().unregister(this);
    }


    private class LowLevel {

        private final ProductsService productsService = ProductsService.getInstance();
        private final List<Product> products = new ArrayList<>();

        private final AtomicBoolean loading = new AtomicBoolean();
        private boolean fullyLoaded;

        Product get(int index) {
            return products.get(index);
        }

        void delete(int index) {
            if (MathUtils.inBounds(index, 0, size())) {
                products.remove(index);
            }
        }

        int size() {
            return products.size();
        }

        boolean reload() {
            logger.info("reload started. loading {}, fullyLoaded {}", loading.get(), fullyLoaded);
            fullyLoaded = false;
            return load(0, products.size());
        }

        boolean load(int offset, int size) {
            boolean started = (!fullyLoaded && loading.compareAndSet(false, true));
            if (started) {
                logger.info("loading started.  Offset {}, size {}", offset, size);
                productsService.get(offset, size, page -> {
                    onPageLoaded(page);
                    logger.info("loading finished. Offset {}, size {}", offset, size);
                    loading.set(false);
                    onLoadingComplete(page);
                });
            }
            return started;
        }

        private void onPageLoaded(ProductsPage page) {
            if (page.size() > page.loadedSize()) {
                fullyLoaded = true;
            }

            if (page.offset() < products.size()) {
                mergePage(page);
                logger.info("Merged offset {} size {}", page.offset(), page.size());
            } else {
                products.addAll(page.get());
                logger.info("Added offset {} size {}", page.offset(), page.size());
            }

            if (fullyLoaded) {
                logger.info("Fully loaded. Data size {}, page size {}, loaded size {}", products.size(), page.size(), page.loadedSize());
            }
        }

        private void mergePage(ProductsPage page) {
            for (int i = 0; i < page.size(); i++) {
                mergeProduct(page.offset() + i, page.get(i));
            }
        }

        private void mergeProduct(int position, Product product) {
            if (position < size()) {
                products.set(position, product);
            } else {
                products.add(product);
            }
        }
    }
}
