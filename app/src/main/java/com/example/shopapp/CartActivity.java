package com.example.shopapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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

        cartList = new ArrayList<>();

        adapter = new CartAdapter(this, cartList, (item, position) -> {
            CartManager.removeItem(item);
            Toast.makeText(CartActivity.this, item.getTitle() + " uklonjeno", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        CartManager.getCartItems(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();
                double total = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ItemModel item = dataSnapshot.getValue(ItemModel.class);
                    if (item != null) {
                        cartList.add(item);
                        total += item.getPrice();
                    }
                }

                adapter.notifyDataSetChanged();
                totalPriceTextView.setText(String.format("%.2f KM", total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "Greška pri učitavanju korpe", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.finishShoppingButton).setOnClickListener(v -> {
            CartManager.clearCart();
            Toast.makeText(this, "Kupovina uspješna!", Toast.LENGTH_SHORT).show();
        });
    }
}