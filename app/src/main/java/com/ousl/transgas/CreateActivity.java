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
    EditText userName, email , address , mobileNumber, password, rePassword;
    TextView loginTextButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();

        userName = (EditText) findViewById(R.id.nameEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        address = (EditText) findViewById(R.id.addressEditText);
        mobileNumber = (EditText) findViewById(R.id.mobileNumberEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        rePassword = (EditText) findViewById(R.id.rePasswordEditText);
        createButton = findViewById(R.id.createButton);
        loginTextButton = findViewById(R.id.loginText);
        googleButton = findViewById(R.id.google_Button);
        facebookButton = findViewById(R.id.facebook_Button);
        DB = new DBHelper(this);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usedName = userName.getText().toString();
                String usedEmail = email.getText().toString();
                String usedAddress = address.getText().toString();
                String usedMobileNumber = mobileNumber.getText().toString();
                String usedPassword = password.getText().toString();
                String usedRePassword = rePassword.getText().toString();

                //Function Validate Inputs
                boolean checkInput=validateInputs(usedName,usedEmail,usedAddress,usedMobileNumber,usedPassword);

                if (checkInput == true){
                    if (usedName.equals("") || usedEmail.equals("") || usedAddress.equals("") || usedMobileNumber.equals("") || usedPassword.equals("") || usedRePassword.equals(""))
                        Toast.makeText(CreateActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    else {
                        if (usedPassword.equals(usedRePassword)) {
                            Boolean checkEmails = DB.checkEmail(usedEmail);
                            if (checkEmails == false) {
                                Boolean createUser = DB.createNewUser(usedName, usedEmail,usedAddress, usedMobileNumber, usedPassword);
                                if (createUser == true) {
                                    Toast.makeText(CreateActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CreateActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                Toast.makeText(CreateActivity.this, "User Already Exist. Try Different Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                        }
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

    //Function for Validate Inputs
    private Boolean validateInputs(String usedName, String usedEmail, String usedAddress, String usedMobileNumber, String usedPassword){
        if(usedName.length()==0) {
            userName.requestFocus();
            userName.setError("Name Required");
            return false;
        }
        else if (!usedName.matches("[a-zA-Z]+")) {
            userName.requestFocus();
            userName.setError("Alphabetic Letters Only");
            return false;
        }
        else if(usedEmail.length()==0) {
            email.requestFocus();
            email.setError("Email Required");
            return false;
        }
        else if (!usedEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z.-]+$")) {
            email.requestFocus();
            email.setError("Email Invalid");
            return false;
        }
        else if(usedAddress.length()==0) {
            address.requestFocus();
            address.setError("Address Required");
            return false;
        }
        else if (!usedAddress.matches("[a-zA-Z0-9.-]+")) {
            address.requestFocus();
            address.setError("Address Invalid");
            return false;
        }
        else if(usedMobileNumber.length()==0) {
            mobileNumber.requestFocus();
            mobileNumber.setError("Mobile Number Required");
            return false;
        }
        else if (!usedMobileNumber.matches("0[0-9]{9}")) {
            mobileNumber.requestFocus();
            mobileNumber.setError("Enter 10 Digit Number With 0");
            return false;
        }
        else if(usedPassword.length()==0) {
            password.requestFocus();
            password.setError("Password Required");
            return false;
        }
        else if (!usedPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=_\\-!¡?¿.,;:<>])(?=\\S+$).{6,}$")) {
            password.requestFocus();
            password.setError("Use Special Characters and Numbers");
            return false;
        }
        else if(usedPassword.length()<=5) {
            password.requestFocus();
            password.setError("Minimum 6 Characters Required");
            return false;
        }
        else {
            return true;
        }
    }
}