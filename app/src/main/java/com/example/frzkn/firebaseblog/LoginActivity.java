package com.example.frzkn.firebaseblog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView logoText;
    private EditText loginEmailTextview;
    private EditText loginPasswordTextview;
    private Button loginLoginButton;
    private Button loginRegisterButton;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialize firebase
        mAuth = FirebaseAuth.getInstance();

        //Referencing
        logoText = findViewById(R.id.login_logo_textview);
        loginEmailTextview = findViewById(R.id.login_email_textview);
        loginPasswordTextview = findViewById(R.id.login_password_textview);
        loginLoginButton = findViewById(R.id.login_login_button);
        loginRegisterButton = findViewById(R.id.login_register_button);
        loginProgressbar = findViewById(R.id.login_progressbar);

        //Setting custom font runtime
        Typeface yellowTail = Typeface.createFromAsset(getAssets(), "fonts/Yellowtail.ttf");
        logoText.setTypeface(yellowTail);

        //Click Listeners

        loginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginEmail = loginEmailTextview.getText().toString();
                String loginPassword = loginPasswordTextview.getText().toString();
                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)) {
                    loginProgressbar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                intentToMain();

                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                loginProgressbar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
                }

            }

        });
        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToRegister();
            }
        });
    }

    private void intentToRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            intentToMain();
        }
    }

    private void intentToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();

    }
}
