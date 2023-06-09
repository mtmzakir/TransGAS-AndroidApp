package com.ousl.transgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    ImageButton googleBtn, facebookBtn;
    EditText email , password;
    TextView createNewAccTextBtn, forgotPasswordTextBtn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        loginBtn = findViewById(R.id.loginButton);
        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        createNewAccTextBtn = findViewById(R.id.createNewAccountText);
        forgotPasswordTextBtn = findViewById(R.id.forgotPasswordText);
        googleBtn = findViewById(R.id.google_Button);
        facebookBtn = findViewById(R.id.facebook_Button);
        DB = new DBHelper(this);

        //Function Login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usedEmail = email.getText().toString();
                String usedPassword = password.getText().toString();

                if (usedEmail.equals("") || usedPassword.equals(""))
                    Toast.makeText(LoginActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkEmailPasswords = DB.checkEmailPassword(usedEmail, usedPassword);
                    if (checkEmailPasswords == true) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("current_user_data",usedEmail);  //User Details Push
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Function Start Create Activity
        createNewAccTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateNewAccountPage();
            }
        });

        //Function Start Forgot Activity
        forgotPasswordTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPasswordPage();
            }
        });

        //Function Google and Facebook Buttons
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToastNotAvailable();
            }
        });
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToastNotAvailable();
            }
        });

    }

    //Open Create New Account Page
    public void openCreateNewAccountPage() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    //Open Forgot Password Page
    public void openForgotPasswordPage() {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    //Open Toast Not Available
    public void openToastNotAvailable() {
        Toast.makeText(this, "Feature Unavailable \uD83D\uDE41", Toast.LENGTH_SHORT).show();
    }

}