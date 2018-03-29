package com.example.frzkn.firebaseblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerPasswordConfirm;
    private Button registerRegisterButton;
    private Button registerLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Initialize
        registerEmail = findViewById(R.id.register_email_textview);
        registerPassword = findViewById(R.id.register_password_textview);
        registerPasswordConfirm = findViewById(R.id.register_password_confirm);
        registerRegisterButton = findViewById(R.id.register_register_button);
        registerLoginButton = findViewById(R.id.register_login_button);
        progressBar = findViewById(R.id.register_progressbar);
        //Initialize mAuth
        mAuth = FirebaseAuth.getInstance();
        //Listners
        registerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confirmPassword = registerPasswordConfirm.getText().toString();
                ;
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    if (!password.equals(confirmPassword)) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(RegisterActivity.this, AccountActivity.class));
                                    finish();
                                    //intentToMain();

                                } else {
                                    String error = "Error:" + task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                } else progressBar.setVisibility(View.INVISIBLE);
            }
        });
        registerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentToLogin();
            }
        });

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
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    private void intentToLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}

