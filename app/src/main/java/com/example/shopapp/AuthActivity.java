package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button authButton;
    private TextView switchAuthMode, authTitle;

    private FirebaseAuth mAuth;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        authButton = findViewById(R.id.authButton);
        switchAuthMode = findViewById(R.id.switchAuthMode);
        authTitle = findViewById(R.id.authTitle);

        switchAuthMode.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            if (isLoginMode) {
                authTitle.setText("Dobrodošli nazad");
                authButton.setText("Prijavi se");
                switchAuthMode.setText("Nemate račun? Registrujte se");
            } else {
                authTitle.setText("Kreirajte račun");
                authButton.setText("Registruj se");
                switchAuthMode.setText("Imate račun? Prijavite se");
            }
        });

        authButton.setOnClickListener(v -> handleAuth());
    }

    private void handleAuth() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Molimo popunite sva polja", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isLoginMode) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            goToMainActivity();
                        } else {
                            Toast.makeText(AuthActivity.this, "Greška pri prijavi: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "Registracija uspješna!", Toast.LENGTH_SHORT).show();
                            goToMainActivity();
                        } else {
                            Toast.makeText(AuthActivity.this, "Greška pri registraciji: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}