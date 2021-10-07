package com.example.myapplication_rentme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication_rentme.Database.CartDbHelper;
import com.example.myapplication_rentme.model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MarketPlaceActivity extends AppCompatActivity {
    private RecyclerView service_recycler;
    private ProgressBar P_Bar;
    private TextView text,home;
    private ArrayList<ProductModel> list = new ArrayList<>();
    private ProductAdapter productAdapter;
    private ImageView cartImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_av);
        P_Bar = findViewById(R.id.avi);
        service_recycler = findViewById(R.id.property_recycler);
        text = findViewById(R.id.text);
        text.setVisibility(View.GONE);
        home= findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarketPlaceActivity.this,HomePage.class));
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MarketPlaceActivity.this);
        service_recycler.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(MarketPlaceActivity.this, list);
        service_recycler.setAdapter(productAdapter);


//        add_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
//            }
//        });


        cartImage = findViewById(R.id.cart_btn_main);

        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MarketPlaceActivity.this, CartActivity.class));



            }
        });


        GetAds();

    }


    private void GetAds() {
        DatabaseReference AdRef = FirebaseDatabase.getInstance().getReference().child("Product-Ads");
        AdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                if (snapshot.exists()) {


                    for (DataSnapshot Data : snapshot.getChildren()) {

                        ProductModel productModel = Data.getValue(ProductModel.class);
                        list.add(productModel);


                    }
                    if (list.size() == 0) {
                        P_Bar.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                    } else {

                        P_Bar.setVisibility(View.GONE);
                        Collections.sort(list, new Comparator<ProductModel>() {
                            @Override
                            public int compare(ProductModel item, ProductModel t1) {
                                String s1 = String.valueOf(item.getTime());
                                String s2 = String.valueOf(t1.getTime());
                                return s2.compareToIgnoreCase(s1);
                            }
                        });
                        productAdapter.notifyDataSetChanged();

                    }


                } else {
                    P_Bar.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        CartDbHelper dbHelper = new CartDbHelper(this);

        String item_np = String.valueOf(dbHelper.getAllItems().size());

        TextView cart_item_no = findViewById(R.id.cart_items_number);
        if (dbHelper.getAllItems().size() > 0) {

            cart_item_no.setVisibility(View.VISIBLE);
            cart_item_no.setText(item_np);
        } else {
            cart_item_no.setVisibility(View.GONE);
        }
    }
}