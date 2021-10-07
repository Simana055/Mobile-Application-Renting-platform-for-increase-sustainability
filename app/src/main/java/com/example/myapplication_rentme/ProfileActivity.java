package com.example.myapplication_rentme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView profile_email,profile_name,profile_goback;
    private FirebaseAuth mAuth;
    LinearLayout layout2,layout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initListeners();


        profile_email.setText(mAuth.getCurrentUser().getEmail());
        profile_name.setText(mAuth.getCurrentUser().getDisplayName());
    }

    public void initView(){

        profile_email = findViewById(R.id.profile_email);
        profile_name =  findViewById(R.id.profile_name);
        profile_goback =  findViewById(R.id.profile_goback);
        layout2 = findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,HelpFeedbackActivity.class));
                finish();
            }
        });
        layout3 = findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,ReportActivity.class));
                finish();
            }
        });

    }

    private void initListeners() {
        profile_goback.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_goback:
                onBackPressed();
                break;
        }
    }
}
