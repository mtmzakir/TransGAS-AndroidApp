package com.ousl.transgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {
    Button createButton;
    EditText name, email , address , mobileNumber, password;
    TextView loginButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        password = (EditText) findViewById(R.id.password);
        createButton = findViewById(R.id.createButton);
        loginButton = findViewById(R.id.loginButton);
        DB = new DBHelper(this);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails = email.getText().toString();
                String passwords = password.getText().toString();
                String names = name.getText().toString();
                String mobileNumbers = mobileNumber.getText().toString();
                String addresses = address.getText().toString();

                if (emails.equals("") || passwords.equals("") || names.equals("") || mobileNumbers.equals("") || addresses.equals(""))
                    Toast.makeText(CreateActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkemail = DB.checkemail(emails);
                    if (checkemail==false) {
                        Boolean insert = DB.insertData(emails,names,addresses,passwords,mobileNumbers);
                        if (insert==true) {
                            Toast.makeText(CreateActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CreateActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else Toast.makeText(CreateActivity.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}