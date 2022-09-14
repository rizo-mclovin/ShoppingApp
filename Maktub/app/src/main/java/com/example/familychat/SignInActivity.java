package com.example.familychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        });

        TextView signUpText = findViewById(R.id.signUpText);
        signUpText.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

    }
}