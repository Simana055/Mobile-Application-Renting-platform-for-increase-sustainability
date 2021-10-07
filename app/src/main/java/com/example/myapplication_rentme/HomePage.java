package com.example.myapplication_rentme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    Button home_profile_btn;
    BottomNavigationView bottomNavigationView;
    private Button add_event;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        Log.d("Checking_Value","  " + mAuth.getCurrentUser().getDisplayName());

        initView();
        initListeners();
    }

    private void initListeners() {
        home_profile_btn.setOnClickListener(this);

    }

    public void initView() {
        home_profile_btn = findViewById(R.id.home_profile_btn);
        add_event = findViewById(R.id.floating_button);

        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, AddProductActivity.class));
            }
        });
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHistory:
                        Intent intent = new Intent(HomePage.this, HistoryActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.menuMarketplace:
                        intent = new Intent(HomePage.this, MarketPlaceActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home_profile_btn:
                intent = new Intent(HomePage.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.menuMarketplace:
                intent = new Intent(HomePage.this, MarketPlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.menuHistory:
                intent = new Intent(HomePage.this, HistoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}