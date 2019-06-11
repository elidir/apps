package com.example.products.views.list.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.main.App;
import com.example.products.R;
import com.example.products.data.objects.Product;
import com.example.products.model.PaginatedProducts;

import butterknife.BindView;
import butterknife.ButterKnife;

class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.Holder> {

    interface ItemActionListener {
        void onAction(int position, Product product);
    }

    private ItemActionListener onEditItemListener;
    private ItemActionListener onDeleteItemListener;
    private PaginatedProducts products;


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Product product = products.get(position);

        holder.setTitle(product.getName());
        holder.setPrice(product.getPrice());
        holder.setAmount(product.getAmount());
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;
    }


    public void setData(@NonNull PaginatedProducts products) {
        this.products = products;
        this.products.setListener(new PaginatedProducts.UpdatesListener() {
            @Override
            public void onProductsChanged(int offset, int size) {
                notifyItemRangeChanged(offset, size);
            }

            @Override
            public void onProductDeleted(int position) {
                notifyItemRemoved(position);
            }
        });
        notifyDataSetChanged();
    }

    void setOnEditItemListener(ItemActionListener onEditItemListener) {
        this.onEditItemListener = onEditItemListener;
    }

    void setOnDeleteItemListener(ItemActionListener onDeleteItemListener) {
        this.onDeleteItemListener = onDeleteItemListener;
    }

    void close() {
        if (products != null) {
            products.close();
        }
    }


    class Holder extends RecyclerView.ViewHolder {

        private final Context context = App.getInstance();

        @BindView(R.id.product_name) TextView titleView;
        @BindView(R.id.product_amount) TextView amountView;
        @BindView(R.id.product_price) TextView priceView;


        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(View::showContextMenu);
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                menu.setHeaderTitle(R.string.product_actions_title);
                menu.add(0, itemView.getId(), 0, R.string.product_actions_edit).setOnMenuItemClickListener(this::onEditClick);
                menu.add(0, itemView.getId(), 1, R.string.product_actions_delete).setOnMenuItemClickListener(this::onDeleteClick);
            });
        }

        void setTitle(String title) {
            titleView.setText(title);
        }

        void setPrice(double price) {
            String text = context.getString(R.string.item_price, price);
            priceView.setText(text);
        }

        void setAmount(long amount) {
            String text = context.getString(R.string.item_amount, amount);
            amountView.setText(text);
        }

        private boolean onEditClick(MenuItem menuItem) {
            onAction(onEditItemListener);
            return true;
        }

        private boolean onDeleteClick(MenuItem menuItem) {
            onAction(onDeleteItemListener);
            return true;
        }

        private void onAction(@Nullable ItemActionListener actionListener) {
            if (actionListener == null) {
                return;
            }
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Product product = products.get(position);
                actionListener.onAction(position, product);
            }
        }
    }

}
