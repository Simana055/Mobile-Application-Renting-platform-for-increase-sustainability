package com.example.myapplication_rentme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button history_goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);

        initView();
        initListeners();



    }

    private void initListeners() {
        history_goback.setOnClickListener(this);
    }

    private void initView() {
        history_goback = findViewById(R.id.history_goback);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_goback:
                onBackPressed();
                break;
        }
    }
}
