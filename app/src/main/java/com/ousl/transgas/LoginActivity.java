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

    Button loginButton;
    ImageButton googleButton, facebookButton;
    EditText email , password;
    TextView createNewAccountTextButton, forgotPasswordTextButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        createNewAccountTextButton = findViewById(R.id.createNewAccountText);
        forgotPasswordTextButton = findViewById(R.id.forgotPasswordText);
        googleButton = findViewById(R.id.google_Button);
        facebookButton = findViewById(R.id.facebook_Button);
        DB = new DBHelper(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails = email.getText().toString();
                String passwords = password.getText().toString();

                if (emails.equals("") || passwords.equals(""))
                    Toast.makeText(LoginActivity.this, "Fields Cannot Be Empty ❗", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkemailpasswords = DB.checkemailpasswords(emails,passwords);
                    if (checkemailpasswords==true) {
                        Toast.makeText(LoginActivity.this, "Login Successful \uD83D\uDE0A", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials ⚠", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        createNewAccountTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencreateNewAccountPage();
            }
        });
        forgotPasswordTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openforgotPasswordPage();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToastNotAvailable();
            }
        });
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToastNotAvailable();
            }
        });

    }
    public void opencreateNewAccountPage() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
    public void openforgotPasswordPage() {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }
    public void openToastNotAvailable() {
        Toast.makeText(this, "Feature Unavailable \uD83D\uDE41", Toast.LENGTH_SHORT).show();
    }

}