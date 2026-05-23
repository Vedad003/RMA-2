package com.example.shopapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView title, price, description;
    Button addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Product details");

        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);

        toolbar.setNavigationOnClickListener(v -> finish());

        image = findViewById(R.id.detailImage);
        title = findViewById(R.id.detailTitle);
        price = findViewById(R.id.detailPrice);
        description = findViewById(R.id.detailDescription);
        addToCart = findViewById(R.id.addToCartButton);

        String t = getIntent().getStringExtra("title");
        String d = getIntent().getStringExtra("description");
        double p = getIntent().getDoubleExtra("price", 0);
        String img = getIntent().getStringExtra("image");

        title.setText(t);

        price.setText(String.format("%.2f KM", p));

        description.setText(d);

        int resId = getResources().getIdentifier(
                img,
                "drawable",
                getPackageName()
        );
        image.setImageResource(resId);

        addToCart.setOnClickListener(v -> {
            Toast.makeText(this, "Kliknuto na dugme!", Toast.LENGTH_SHORT).show();
            ItemModel item = new ItemModel();
            item.setId("temp_id");
            item.setTitle(t);
            item.setPrice(p);
            item.setDescription(d);
            item.setImagePath(img);

            CartManager.addToCart(item);

            Toast.makeText(
                    this,
                    "Added to cart",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}