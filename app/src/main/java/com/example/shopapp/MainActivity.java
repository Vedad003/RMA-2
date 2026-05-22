package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchBar;
    ImageButton cartButton; // Promijenjeno u ImageButton

    ArrayList<ItemModel> itemList;
    ArrayList<ItemModel> filteredList;
    ArrayList<CategoryModel> categoryList;

    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchBar = findViewById(R.id.searchBar);
        cartButton = findViewById(R.id.cartButton);

        itemList = new ArrayList<>();
        filteredList = new ArrayList<>();
        categoryList = new ArrayList<>();

        loadJson();

        filteredList.addAll(itemList);

        adapter = new ProductAdapter(
                this,
                filteredList,
                categoryList,
                item -> {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("price", item.getPrice());
                    intent.putExtra("description", item.getDescription());
                    intent.putExtra("image", item.getImagePath());
                    startActivity(intent);
                });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        // Otvaranje korpe na klik gumba
        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterProducts(String text) {
        filteredList.clear();
        for (ItemModel item : itemList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void loadJson() {
        try {
            InputStream is = getAssets().open("database.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject object = new JSONObject(json);

            JSONArray categoryArray = object.getJSONArray("Category");
            for (int i = 0; i < categoryArray.length(); i++) {
                JSONObject categoryObject = categoryArray.getJSONObject(i);
                CategoryModel category = new CategoryModel();
                category.setId(categoryObject.getInt("Id"));
                category.setName(categoryObject.getString("Name"));
                categoryList.add(category);
            }

            JSONArray itemArray = object.getJSONArray("Items");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject itemObject = itemArray.getJSONObject(i);
                ItemModel item = new ItemModel();
                item.setId(itemObject.getInt("Id"));
                item.setTitle(itemObject.getString("Title"));
                item.setPrice(itemObject.getDouble("Price"));
                item.setImagePath(itemObject.getString("ImagePath"));
                item.setCategoryId(itemObject.getInt("CategoryId"));
                item.setDescription(itemObject.getString("Description"));
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}