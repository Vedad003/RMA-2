package com.example.shopapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter
        extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;

    ArrayList<ItemModel> itemList;
    ArrayList<CategoryModel> categoryList;

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(ItemModel item);
    }

    public ProductAdapter(Context context,
                          ArrayList<ItemModel> itemList,
                          ArrayList<CategoryModel> categoryList,
                          OnItemClickListener listener) {

        this.context = context;
        this.itemList = itemList;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_product,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        ItemModel item = itemList.get(position);

        holder.title.setText(item.getTitle());

        holder.price.setText(String.format("$%.2f", item.getPrice()));

        holder.category.setText(
                getCategoryName(item.getCategoryId()));

        int imageRes = context.getResources()
                .getIdentifier(
                        item.getImagePath(),
                        "drawable",
                        context.getPackageName()
                );

        holder.image.setImageResource(imageRes);

        holder.itemView.setOnClickListener(v ->
                listener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {

        ImageView image;

        TextView title, price, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(
                    R.id.productImage);

            title = itemView.findViewById(
                    R.id.productTitle);

            price = itemView.findViewById(
                    R.id.productPrice);

            category = itemView.findViewById(
                    R.id.productCategory);
        }
    }

    private String getCategoryName(int categoryId) {

        for (CategoryModel category : categoryList) {

            if (category.getId() == categoryId) {
                return category.getName();
            }
        }

        return "";
    }
}