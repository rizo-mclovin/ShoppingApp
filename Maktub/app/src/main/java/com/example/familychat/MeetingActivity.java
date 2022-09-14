package com.example.familychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeetingActivity.this, SignInActivity.class));
                finish();
            }
        });
    }
}