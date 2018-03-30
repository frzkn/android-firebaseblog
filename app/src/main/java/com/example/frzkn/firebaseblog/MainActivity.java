package com.example.frzkn.firebaseblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize firebase
        mAuth = FirebaseAuth.getInstance();
        intent = new Intent(MainActivity.this, LoginActivity.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getActionBar() != null) {
            getActionBar().setTitle("Home");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "User logged in ", Toast.LENGTH_SHORT).show();

        } else if (currentUser == null) {
            intentToLogin();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                firebaseLogout();
            case R.id.action_account:
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            default:
                return false;
        }
    }

    private void firebaseLogout() {
        mAuth.signOut();
        intentToLogin();

    }


    private void intentToLogin() {
        Toast.makeText(this, "User not logged in ", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        //User cannot return to this screen again if they press the back button
        //Intent will be cleared
        finish();
    }

}