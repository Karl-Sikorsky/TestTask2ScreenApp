package com.example.testtask2screenapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    TextView userAddress, userPhone, userMail, userName, userAge;
    ImageView userPicture;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userAddress = findViewById(R.id.textViewAdress);
        userName = findViewById(R.id.textViewName);
        userAge = findViewById(R.id.textViewAge);
        userMail = findViewById(R.id.textViewMail);
        userPhone = findViewById(R.id.textViewCellPhone);
        userPicture = findViewById(R.id.imageView);
        intent = getIntent();
        setUserData();
        userPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneCall(userPhone.getText().toString());
            }
        });
    }

    private void phoneCall(String s) {
        if (isPermissionGranted()) {
            call_action(s);
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    call_action(userPhone.getText().toString());
                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void call_action(String s) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + s));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(callIntent);
    }

    private void setUserData() {
        if (intent.getStringExtra("name") != null) {
            userName.setText(intent.getStringExtra("name"));
        }
        if (intent.getStringExtra("phone") != null) {
            userPhone.setText(intent.getStringExtra("phone"));
        }
        if (intent.getStringExtra("address") != null) {
            userAddress.setText(intent.getStringExtra("address"));
        }
        if (intent.getStringExtra("age") != null) {
            userAge.setText(intent.getStringExtra("age"));
            if (intent.getStringExtra("gender") != null) {

                userAge.setText(String.format("%s, %s", userAge.getText().toString(), intent.getStringExtra("gender")));
            }
        }
        if (intent.getStringExtra("mail") != null) {
            userMail.setText(intent.getStringExtra("mail"));
        }
        if (intent.getStringExtra("image") != null) {
            Picasso.get()
                    .load(intent.getStringExtra("image"))
                    .placeholder(getApplicationContext().getResources().getDrawable(R.drawable.ic_launcher_background))
                    .error(getApplicationContext().getResources().getDrawable(R.drawable.ic_launcher_background))
                    .fit()
                    .into(userPicture);
        }

    }
}
