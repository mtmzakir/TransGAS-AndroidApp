package com.ousl.transgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ousl.transgas.model.UserModel;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    String currentUserDetails;
    Button updateDetails;
    EditText userEmail,userPhone,userAddress;
    TextView userName;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        //Parse Database Data
        userName = findViewById(R.id.userNameText);
        userEmail = findViewById(R.id.userEmailEditText);
        userAddress = findViewById(R.id.userAddressEditText);
        userPhone = findViewById(R.id.userPhoneEditText);

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");
        DB = new DBHelper(this);

        //Function Update Details Button
        updateDetails = findViewById(R.id.updateButton);
        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usedName = userName.getText().toString();
                String usedEmail = userEmail.getText().toString();
                String usedAddress = userAddress.getText().toString();
                String usedPhoneNumber = userPhone.getText().toString();

                //Function Validate Inputs
                boolean checkInput=validateInputs(usedEmail,usedAddress,usedPhoneNumber);

                if (checkInput == true){
                    if (usedEmail.equals("") || usedAddress.equals("") || usedPhoneNumber.equals(""))
                        Toast.makeText(EditProfileActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    else {
                        boolean updateNewDetails = DB.updateProfileDetails(usedName, usedEmail,usedAddress, usedPhoneNumber);
                        if(updateNewDetails==true){
                            Toast.makeText(EditProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                            intent.putExtra("current_user_data",currentUserDetails);  //User Details Push
                            startActivity(intent);
                        }else {
                            Toast.makeText(EditProfileActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        getUserDetails();
    }

    //Function Get Current User Details
    public void getUserDetails(){
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<UserModel> aList = dbHelper.getCurrentUserDetails(currentUserDetails);
        UserModel userModel = aList.get(0);
        userName.setText(userModel.getUsed_name());
        userEmail.setText(userModel.getUsed_email());
        userAddress.setText(userModel.getUsed_address());
        userPhone.setText(userModel.getUsed_phone());
    }

    //Function for Validate Inputs
    private Boolean validateInputs(String usedEmail, String usedAddress, String usedMobileNumber){
        if(usedEmail.length()==0) {
            userEmail.requestFocus();
            userEmail.setError("Email Required");
            return false;
        }
        else if (!usedEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z.-]+$")) {
            userEmail.requestFocus();
            userEmail.setError("Email Invalid");
            return false;
        }
        else if(usedAddress.length()==0) {
            userAddress.requestFocus();
            userAddress.setError("Address Required");
            return false;
        }
        else if (!usedAddress.matches("[a-zA-Z0-9.-]+")) {
            userAddress.requestFocus();
            userAddress.setError("Address Invalid");
            return false;
        }
        else if(usedMobileNumber.length()==0) {
            userPhone.requestFocus();
            userPhone.setError("Mobile Number Required");
            return false;
        }
        else if (!usedMobileNumber.matches("0[0-9]{9}")) {
            userPhone.requestFocus();
            userPhone.setError("Enter 10 Digit Number With 0");
            return false;
        }
        else {
            return true;
        }
    }
}