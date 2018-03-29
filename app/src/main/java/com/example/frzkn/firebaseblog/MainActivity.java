package com.example.frzkn.firebaseblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, LoginActivity.class);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this,"User logged in ",Toast.LENGTH_SHORT).show();

        } else if (currentUser == null){
            Toast.makeText(this,"User not logged in ",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            //User cannot return to this screen again if they press the back button
            //Intent will be cleared
            finish();
        }
    }

}
