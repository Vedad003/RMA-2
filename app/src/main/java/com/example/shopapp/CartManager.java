package com.example.shopapp;

import java.util.ArrayList;

public class CartManager {

    private static ArrayList<ItemModel> cartItems =
            new ArrayList<>();

    public static void addToCart(ItemModel item) {

        cartItems.add(item);
    }

    public static ArrayList<ItemModel> getCartItems() {

        return cartItems;
    }

    public static void removeItem(ItemModel item) {

        cartItems.remove(item);
    }

    public static void clearCart() {

        cartItems.clear();
    }

    
}