package com.example.shopapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView title, price, description;
    Button btnAddToCart;

    public static ArrayList<ItemModel> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Product details");
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(v -> finish());

        image = findViewById(R.id.detailImage);
        title = findViewById(R.id.detailTitle);
        price = findViewById(R.id.detailPrice);
        description = findViewById(R.id.detailDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        String t = getIntent().getStringExtra("title");
        String d = getIntent().getStringExtra("description");
        double p = getIntent().getDoubleExtra("price", 0);
        String img = getIntent().getStringExtra("image");

        title.setText(t);
        price.setText("$" + p);
        description.setText(d);

        int resId = getResources().getIdentifier(img, "drawable", getPackageName());
        image.setImageResource(resId);

        btnAddToCart.setOnClickListener(v -> {

            ItemModel item = new ItemModel();
            item.setTitle(t);
            item.setPrice(p);
            item.setImagePath(img);
            item.setDescription(d);

            cartList.add(item);

            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }
}