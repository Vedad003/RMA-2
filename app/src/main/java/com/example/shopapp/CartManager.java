package com.example.shopapp;

import java.util.ArrayList;

public class CartManager {

    private static ArrayList<ItemModel> cartItems =
            new ArrayList<>();

    // ADD TO CART
    public static void addToCart(ItemModel item) {

        cartItems.add(item);
    }

    // GET CART ITEMS
    public static ArrayList<ItemModel> getCartItems() {

        return cartItems;
    }

    // REMOVE ITEM
    public static void removeItem(ItemModel item) {

        cartItems.remove(item);
    }

    // CLEAR CART
    public static void clearCart() {

        cartItems.clear();
    }

    
}