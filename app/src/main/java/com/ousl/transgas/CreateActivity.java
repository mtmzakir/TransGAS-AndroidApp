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

public class CreateActivity extends AppCompatActivity {
    Button createButton;
    ImageButton googleButton, facebookButton;
    EditText name, email , address , mobileNumber, password, repassword;
    TextView loginTextButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        createButton = findViewById(R.id.createButton);
        loginTextButton = findViewById(R.id.loginText);
        googleButton = findViewById(R.id.google_Button);
        facebookButton = findViewById(R.id.facebook_Button);
        DB = new DBHelper(this);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = name.getText().toString();
                String emails = email.getText().toString();
                String addresses = address.getText().toString();
                String mobileNumbers = mobileNumber.getText().toString();
                String passwords = password.getText().toString();
                String repasswords = repassword.getText().toString();

                if (names.equals("") || emails.equals("") || addresses.equals("") || mobileNumbers.equals("") || passwords.equals("")|| repasswords.equals(""))
                    Toast.makeText(CreateActivity.this, "❗Enter All Fields", Toast.LENGTH_SHORT).show();
                else {
                    if (passwords.equals(repasswords)) {
                    Boolean checkemail = DB.checkemail(emails);
                    if (checkemail==false) {
                        Boolean insert = DB.insertData(names,emails,addresses,mobileNumbers,passwords);
                        if (insert==true) {
                            Toast.makeText(CreateActivity.this, "Account Create Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CreateActivity.this, "⚠ Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else Toast.makeText(CreateActivity.this, "❗User Already Exist", Toast.LENGTH_SHORT).show();
                } else {
                        Toast.makeText(CreateActivity.this, "⚠ Password Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        loginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
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
    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openToastNotAvailable() {
        Toast.makeText(this, "Feature Unavailable \uD83D\uDE41", Toast.LENGTH_SHORT).show();
    }
}