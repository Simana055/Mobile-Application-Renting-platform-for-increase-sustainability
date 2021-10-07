package com.example.myapplication_rentme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication_rentme.Database.CartDbHelper;
import com.example.myapplication_rentme.model.ProductModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    ProductModel productModel;

    DatabaseReference mDatabaseForProductUploading;
    FirebaseAuth mAuth;

    ArrayList<ReviewModel> list = new ArrayList<>();
    ReviewAdapter reviewAdapter;
    private RecyclerView reviewsrecyclerView;


    private TextView Category_Name_View;
    TextView productName, productPrice, productDisc;
    ImageView productImage;
    Button AddToCart, home, add_review;
    ProgressDialog progressDialog;

//    ArrayList<ReviewModel> list = new ArrayList<>();
//    ReviewAdapter reviewAdapter;


//    private RecyclerView reviewsrecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productModel = (ProductModel) getIntent().getSerializableExtra("Product");


        Category_Name_View = findViewById(R.id.Category_View_Id);
        Category_Name_View.setText(productModel.getCategory());
        mAuth = FirebaseAuth.getInstance();
        reviewsrecyclerView = findViewById(R.id.reviews);
        reviewsrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(list, ProductDetailActivity.this);
        reviewsrecyclerView.setAdapter(reviewAdapter);


        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, HomePage.class));
                finish();
            }
        });

        add_review = findViewById(R.id.add_review);
        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_dialoge();
                Review_Dialoge.show();
            }
        });

        initViews();

        GetReviews(productModel.getId());

    }


    private void GetReviews(final String productId) {

        DatabaseReference Review_Ref = FirebaseDatabase.getInstance().getReference().child("Reviews");

        Review_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        ReviewModel reviewModel = dataSnapshot.getValue(ReviewModel.class);

                        Log.d("Checking_Value", " Product_ID " + productId + " ID " + reviewModel.getProduct_id());

                        if (reviewModel.getProduct_id().equals(productId)) {

                            list.add(reviewModel);
                        }

                    }
                    reviewAdapter.notifyDataSetChanged();


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initViews() {

//        mDatabaseForProductUploading = FirebaseDatabase.getInstance().getReference().child("Categories").child(productModel.getCatId()).child("Products");


//        Selected_Category_ID = productModel.getCatId();
//        Selected_Category_Name = productModel.getCatName();

        progressDialog = new ProgressDialog(this);

        productName = findViewById(R.id.product_name_edit_add);
        productName.setText(productModel.getTitle());
        productDisc = findViewById(R.id.product_disc_edit_add);
        productDisc.setText(productModel.getDescription());
        productImage = findViewById(R.id.product_image_add);
        productPrice = findViewById(R.id.actual_price);
        productPrice.setText(productModel.getPrice() + " $$");

        Glide.with(this).load(productModel.getImage()).into(productImage);
//        productImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CropImage.activity()
//                        .setAspectRatio(1, 1)
//                        .start(ProductDetailPage.this);
//            }
//        });
        AddToCart = findViewById(R.id.add_product_btn);
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_toCardDialog(productModel);


            }
        });


    }

    int number = 1;

    private void add_toCardDialog(final ProductModel model) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        //setting up the layout for alert dialog
        View view1 = LayoutInflater.from(this).inflate(R.layout.add_to_cart_dialog, null, false);

        builder1.setView(view1);

        TextView foodDisc = view1.findViewById(R.id.food_disc_dialog);
        foodDisc.setText(model.getDescription());

        TextView foodName = view1.findViewById(R.id.food_name_dialog);
        foodName.setText(model.getTitle());
        TextView foodPrice = view1.findViewById(R.id.food_price_food_dialog);
        foodPrice.setText(model.getPrice() + " $$");
        ImageView foodImage = view1.findViewById(R.id.food_image_add_card_dialog);
        Glide.with(ProductDetailActivity.this).load(model.getImage()).into(foodImage);
        final TextView quantity_text = view1.findViewById(R.id.quantity_text);
        ImageView addBtn = view1.findViewById(R.id.add_quantity);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                quantity_text.setText(String.valueOf(number));
            }
        });

        ImageView minusBtn = view1.findViewById(R.id.remove_quantity);
        Button addToCartBtn = view1.findViewById(R.id.add_toCartFinal);
        Button cancelBtn = view1.findViewById(R.id.cancel_btn_dialog);

        final AlertDialog dialogg = builder1.create();
        dialogg.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = 1;
                dialogg.cancel();
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ne = number--;
                if (ne <= 1) {
                    number++;
                } else {
                    quantity_text.setText(String.valueOf(number));
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(model);
                dialogg.dismiss();


            }
        });
    }

    private void addToCart(ProductModel model) {
        CartProductModel cartProductModel = new CartProductModel();

        cartProductModel.setProductId(model.getId());
        cartProductModel.setProductName(model.getTitle());
        cartProductModel.setProductImage(model.getImage());
        cartProductModel.setProductDiscription(model.getDescription());
        cartProductModel.setProductPrice(model.getPrice());
        cartProductModel.setCatName(model.getCategory());
        cartProductModel.setQuantity(number);


        CartDbHelper dbHelper = new CartDbHelper(ProductDetailActivity.this);
        double response = dbHelper.insertItem(cartProductModel);
        if (response == -1) {
            Toast.makeText(ProductDetailActivity.this, "Not Added", Toast.LENGTH_LONG).show();
        } else {


            Toast.makeText(ProductDetailActivity.this, "Added to Cart.", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBackItem(View view) {
        onBackPressed();


    }

    Dialog Review_Dialoge;
    EditText Review_Text;
    RatingBar ratingBar;
    Button Submit_Review;
    String review_text;

    public void review_dialoge() {


        Review_Dialoge = new Dialog(ProductDetailActivity.this);
        Review_Dialoge.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Review_Dialoge.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Review_Dialoge.setCancelable(true);
        Review_Dialoge.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT
        );
        Review_Dialoge.setContentView(R.layout.review_dialoge);

        Review_Text = Review_Dialoge.findViewById(R.id.review_text);
        ratingBar = Review_Dialoge.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        float step_Size = 0.5F;
        ratingBar.setStepSize(step_Size);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
            }
        });
        Submit_Review = Review_Dialoge.findViewById(R.id.add_review);


        Submit_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_text = Review_Text.getText().toString();

                if (review_text.equals("")) {
                    Toast.makeText(ProductDetailActivity.this, "Write Review", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ratingBar.getRating() == 0) {
                    Toast.makeText(ProductDetailActivity.this, "Please add rating", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference ReviewRef = FirebaseDatabase.getInstance().getReference().child("Reviews");
                String review_id = ReviewRef.push().getKey();

                ReviewModel reviewModel = new ReviewModel(review_text, mAuth.getCurrentUser().getEmail(), productModel.getId(), review_id, String.valueOf(System.currentTimeMillis()), ratingBar.getRating());

                ReviewRef.child(review_id).setValue(reviewModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProductDetailActivity.this, "Review & Rating Added", Toast.LENGTH_SHORT).show();
                        Review_Dialoge.dismiss();
                    }
                });





            }
        });


    }

}