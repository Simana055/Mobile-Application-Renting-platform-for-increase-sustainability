package com.example.myapplication_rentme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication_rentme.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

public class AddProductActivity extends AppCompatActivity {

    EditText title, price, description;
    Spinner select_category;
    ImageView product_image;
    TextView image_text;
    CheckBox checkBox1, checkBox2;
    Button post;
    Uri Image_uri;
    DatabaseReference AdRef;
    StorageReference ImageRef;
    String ad_id;
    private UploadingDialoge dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        title = findViewById(R.id.service_name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        select_category = findViewById(R.id.cat_spinner);
        product_image = findViewById(R.id.image);
        image_text = findViewById(R.id.image_text);
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        post = findViewById(R.id.post);

        AdRef = FirebaseDatabase.getInstance().getReference().child("Product-Ads");
        ad_id = AdRef.push().getKey();
        dialog = new UploadingDialoge(this);
        dialog.Set_Full_Width();


        String[] categories = new String[]
                {"Home & Garden", "Salons", "Electronics", "Automobile", "Education", "Sports & Fitness", "Books"
                };

        ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(AddProductActivity.this,
                android.R.layout.simple_spinner_item, categories);

        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_category.setAdapter(category_adapter);

        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .start(AddProductActivity.this);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().equals("")) {
                    title.setError("Field required");
                    return;
                }
                if (price.getText().toString().equals("")) {
                    price.setError("Field required");
                    return;
                }
                if (description.getText().toString().equals("")) {
                    description.setError("Field required");
                    return;
                }
                if (Image_uri == null) {
                    Toast.makeText(AddProductActivity.this, "Product Image is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkBox1.isChecked()) {
                    checkBox1.setError("Accept Conditions");
                    return;
                }

                if (!checkBox2.isChecked()) {
                    checkBox2.setError("Accept Conditions");
                    return;
                }

                UploadData();
            }
        });


    }

    private void UploadData() {

        dialog.Set_Title("Uploading Product Image..");
        dialog.Cancel_Able(false);
        dialog.Show();


        ImageRef = FirebaseStorage.getInstance().getReference().child("ProductImage").child(ad_id);
        ImageRef.putFile(Image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ProductModel productModel = new ProductModel(title.getText().toString(), price.getText().toString(), description.getText().toString(), select_category.getSelectedItem().toString(), uri.toString(), String.valueOf(System.currentTimeMillis()), ad_id);
                        AdRef.child(ad_id).setValue(productModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.Set_Title("Submitting Product Data..");

                                Toast.makeText(AddProductActivity.this, "Ad Posted Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddProductActivity.this, HomePage.class));
                                finish();
                            }
                        });

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                dialog.Set_Percetage((int) progress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProductActivity.this, "Error!  " + e, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final CropImage.ActivityResult result = CropImage.getActivityResult(data);


        if (resultCode == RESULT_OK) {
            Image_uri = result.getUri();
            Glide.with(AddProductActivity.this).load(Image_uri).into(product_image);
            image_text.setVisibility(View.GONE);
        }

    }
}