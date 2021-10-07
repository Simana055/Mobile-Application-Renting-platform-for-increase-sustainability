package com.example.myapplication_rentme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication_rentme.Database.CartDbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    public static TextView totalPriceTextCart;
    public static String totalPriceTextCar;
    public static double total;

    RecyclerView recyclerView;
    CartAdapter adapter;
    List<CartProductModel> list = new ArrayList<>();

    CartDbHelper dbHelper;
    public static String editname,editaddress,editnumber;



    //setting and getting main activity
    private static CartActivity cartActivity;

    public static CartActivity getCartActivity() {
        return cartActivity;
    }

    private static void setCartActivity(CartActivity cartActivity) {
        CartActivity.cartActivity = cartActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        CartActivity.setCartActivity(this);
//        GetUserData();
//        Navigation_Drawer();

        dbHelper = new CartDbHelper(this);

        totalPriceTextCart = findViewById(R.id.total_price_text_cart);

        recyclerView = findViewById(R.id.cart_recycler);
        adapter = new CartAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.add_more_cart_act).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,MarketPlaceActivity.class));
                finish();
            }
        });

        findViewById(R.id.continueBtn_cart_act).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    startActivity(new Intent(CartActivity.this, PaymentMethodActivity.class).putExtra("Orders", (Serializable) list));



                } else {
                    Toast.makeText(CartActivity.this, "You don't have any item in your cart to Continue.", Toast.LENGTH_LONG).show();


                }
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();

        list.clear();
        total = 0;
        List<CartProductModel> models = new ArrayList<>(dbHelper.getAllItems());

        for (CartProductModel mod : models) {
            list.add(mod);

            total = total + (mod.getQuantity() * Double.parseDouble(mod.getProductPrice()));

        }

        String formattedValue = String.format("%.2f", total);


        totalPriceTextCar =  formattedValue;
        totalPriceTextCart.setText("Rs."+ String.valueOf(total));
        adapter.notifyDataSetChanged();
    }

    public void recallOnResume() {
        onResume();
    }
    public void onClickBackItem(View view) {
        onBackPressed();


    }
}