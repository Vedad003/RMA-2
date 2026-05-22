package com.example.shopapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView totalPriceTextView;
    CartAdapter adapter;
    ArrayList<ItemModel> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.cartTotalPrice);

        cartList = CartManager.getCartItems();

        adapter = new CartAdapter(this, cartList, (item, position) -> {
            CartManager.removeItem(item);

            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, cartList.size());

            calculateTotalPrice();

            Toast.makeText(CartActivity.this, item.getTitle() + " removed", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        double total = 0;
        for (ItemModel item : cartList) {
            total += item.getPrice();
        }
        totalPriceTextView.setText(String.format("$%.2f", total));
    }
}