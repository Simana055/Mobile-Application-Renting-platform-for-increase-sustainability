package com.example.myapplication_rentme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication_rentme.Database.CartContract;
import com.example.myapplication_rentme.Database.CartDbHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity {
    List<CartProductModel> list = new ArrayList<>();


    Button continueBtn;
    private String totalPriceText;

    RelativeLayout orderCompleteLayout, main_layout;
    Button backToHomeBtn, home;
    TextView totalPrice;
    CardView card;
    EditText email, password;
    double Total;

    RadioGroup paymentMethod;

    ImageView cartImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);


        list = (List<CartProductModel>) getIntent().getSerializableExtra("Orders");

        orderCompleteLayout = findViewById(R.id.complete_order_layout);
        orderCompleteLayout.setVisibility(View.GONE);
        backToHomeBtn = findViewById(R.id.backtohome);
        continueBtn = findViewById(R.id.continueBtn_payment);
        main_layout = findViewById(R.id.main_layout);
        card = findViewById(R.id.card);
        card.setVisibility(View.GONE);
        email = findViewById(R.id.email_edit_login);
        password = findViewById(R.id.password_edit_login);
        paymentMethod = findViewById(R.id.payment_method);

        totalPrice = findViewById(R.id.total_price_text_payment);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentMethodActivity.this, HomePage.class));
                finish();
            }
        });

        paymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.paypal) {

                    card.setVisibility(View.VISIBLE);

                } else {
                    card.setVisibility(View.GONE);
                }
            }
        });

        cartImage = findViewById(R.id.cart_btn_main);

        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });


        totalPriceText = CartActivity.totalPriceTextCar;
        totalPrice.setText("Total Charges " + totalPriceText + " $$");


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (paymentMethod.getCheckedRadioButtonId()) {
                    case R.id.radioCash:
                        placeOrder();
                        break;
                    case R.id.paypal:
                        if (email.getText().toString().equals("")) {
                            email.setError("Enter Email");
                            return;
                        }
                        if (password.getText().toString().equals("")) {
                            password.setError("Enter Password");
                            return;
                        }
                        continueBtn.setEnabled(false);

                        placeOrder();
                        break;

                    default:
                }

            }
        });
    }

    ProgressDialog progressDialog;

    private void placeOrder() {


        progressDialog = new ProgressDialog(this);

        int count = 1;

        CartDbHelper dbHelper = new CartDbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int i = db.delete(CartContract.CARTTABLE, "1", null);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
        String orderNumber = mDatabase.push().getKey();
        for (int ii = 0; ii < list.size(); ii++) {
            CartProductModel model = list.get(ii);
            model.setId(count);
            count++;
            mDatabase.child(String.valueOf(ii)).setValue(model);
        }
        double ordertime = System.currentTimeMillis();


        HashMap Data = new HashMap();
        Data.put("NumberOfFoods", list.size());
        Data.put("OrderId", orderNumber);
        Data.put("TotalCost", totalPrice.getText().toString());
        Data.put("Status", "New");
        Data.put("Order_Time", ordertime);


        mDatabase.child(orderNumber).updateChildren(Data).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(PaymentMethodActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                orderCompleteLayout.setVisibility(View.VISIBLE);
                backToHomeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(PaymentMethodActivity.this, MarketPlaceActivity.class));
                        finish();
                    }
                });
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