package com.example.frzkn.firebaseblog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Uri profilePhotoURI = null;
    private EditText accountName;
    private CircleImageView profilePhoto;
    private Button accountSaveButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //Initialize
        accountName = findViewById(R.id.account_profile_name);
        accountSaveButton = findViewById(R.id.account_save_button);
        toolbar = findViewById(R.id.account_toolbar);
        profilePhoto = findViewById(R.id.profile_photo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");


        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(AccountActivity.this);
                    }

                }
            }
        });

        accountSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = accountName.getText().toString();
                if (TextUtils.isEmpty(name))
                    Toast.makeText(AccountActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();

                if (profilePhotoURI == null)
                    Toast.makeText(AccountActivity.this, "Please keep a profile photo :) ", Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(name) && profilePhotoURI != null) {

                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profilePhotoURI = result.getUri();
                profilePhoto.setImageURI(profilePhotoURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(AccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}


