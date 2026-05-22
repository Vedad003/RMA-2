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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemModel> cartList;

    public CartAdapter(Context context, ArrayList<ItemModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ISPRAVLJENO: Koristi se item_cart umjesto item_product
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = cartList.get(position);

        holder.title.setText(item.getTitle());
        holder.price.setText(String.format("$%.2f", item.getPrice()));

        int imageRes = context.getResources().getIdentifier(
                item.getImagePath(),
                "drawable",
                context.getPackageName()
        );
        holder.image.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // ISPRAVLJENO: Vežemo se na ID-ove koji stvarno postoje u item_cart.xml
            image = itemView.findViewById(R.id.cartImage);
            title = itemView.findViewById(R.id.cartTitle);
            price = itemView.findViewById(R.id.cartPrice);
        }
    }
}