package com.example.shopapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartManager {

    private static DatabaseReference getUserCartRef() {

        String uid = FirebaseAuth.getInstance()
                .getCurrentUser()
                .getUid();

        String databaseUrl = "https://shopapp-40eb7-default-rtdb.europe-west1.firebasedatabase.app/";

        return FirebaseDatabase
                .getInstance(databaseUrl)
                .getReference()
                .child("Carts")
                .child(uid);
    }

    public static void addToCart(ItemModel item) {

        DatabaseReference cartRef = getUserCartRef();

        String itemId = cartRef.push().getKey();

        if (itemId != null) {

            item.setId(itemId);

            cartRef.child(itemId).setValue(item);
        }
    }

    public static void getCartItems(ValueEventListener listener) {

        getUserCartRef().addValueEventListener(listener);
    }

    public static void removeItem(ItemModel item) {

        if (item.getId() != null) {

            getUserCartRef()
                    .child(item.getId())
                    .removeValue();
        }
    }

    public static void clearCart() {

        getUserCartRef().removeValue();
    }

}