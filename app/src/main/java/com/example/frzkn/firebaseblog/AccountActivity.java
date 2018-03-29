package com.example.frzkn.firebaseblog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText accountName;
    private CircleImageView profilePhoto;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = findViewById(R.id.account_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        profilePhoto = findViewById(R.id.profile_photo);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AccountActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(AccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    Toast.makeText(AccountActivity.this, "You already have Permission", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
