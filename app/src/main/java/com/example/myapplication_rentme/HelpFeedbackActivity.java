package com.example.myapplication_rentme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HelpFeedbackActivity extends AppCompatActivity {

    Button Home, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);

        Home = findViewById(R.id.Home);
        back = findViewById(R.id.back);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpFeedbackActivity.this, HomePage.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpFeedbackActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }
}