package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdit, passwordEdit;
    private Button loginBtn, registerBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        emailEdit = findViewById(R.id.editEmail);
        passwordEdit = findViewById(R.id.editPassword);
        loginBtn = findViewById(R.id.btnLogin);
        registerBtn = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar); // Add in XML
        mAuth = FirebaseAuth.getInstance();

        // Login Button
        loginBtn.setOnClickListener(v -> loginUser());

        // Register Button
        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void loginUser() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (!validateInput(email, password)) return;

        progressBar.setVisibility(ProgressBar.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(this, "Login Successful 🎉", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void registerUser() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (!validateInput(email, password)) return;

        progressBar.setVisibility(ProgressBar.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(this, "Registered Successfully ✅", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Input Validation
    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("Invalid email format");
            emailEdit.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            passwordEdit.setError("Password must be at least 6 characters");
            passwordEdit.requestFocus();
            return false;
        }
        return true;
    }
}
