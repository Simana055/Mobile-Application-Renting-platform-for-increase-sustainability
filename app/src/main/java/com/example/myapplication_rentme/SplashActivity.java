package com.example.myapplication_rentme;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Connect to internet and try again!", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                if (checkPermission()) {
                    afterPermissions();
                } else {

                    requestPermission();
                }


            }

        }, 5000);

    }

    private void afterPermissions() {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

//        if(mAuth.getCurrentUser()!=null){
//
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
//        else {
//            Intent i = new Intent(getApplicationContext(), LogInActivity.class);
//            startActivity(i);
//            finish();
//        }

    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,


                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean READStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (READStorage && WriteStorage) {

                        afterPermissions();


//                        Toast.makeText(SplashActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        requestPermission();
//                        Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}