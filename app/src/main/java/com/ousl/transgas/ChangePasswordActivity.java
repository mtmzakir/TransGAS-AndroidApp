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

public class ChangePasswordActivity extends AppCompatActivity {

    String currentUserDetails;
    Button updatePasswordButton;
    TextView userName,userEmail,userPhone;
    EditText oldPassword,newPassword,reNewPassword;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();

        //Parse Database Data
        userName = findViewById(R.id.userNameText);
        userEmail = findViewById(R.id.userEmailText);
        userPhone = findViewById(R.id.userPhoneText);
        oldPassword = findViewById(R.id.editOldPasswordEditText);
        newPassword = findViewById(R.id.editNewPasswordEditText);
        reNewPassword = findViewById(R.id.editReNewPasswordEditText);

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");
        DB = new DBHelper(this);

        //Function Update Password Button
        updatePasswordButton = findViewById(R.id.updatePasswordButton);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usedEmail = userEmail.getText().toString();
                String usedOldPassword = oldPassword.getText().toString();
                String usedNewPassword = newPassword.getText().toString();
                String usedReNewPassword = reNewPassword.getText().toString();

                //Function Validate Inputs
                boolean checkInput = validateInputs(usedOldPassword, usedNewPassword, usedReNewPassword);

                if (checkInput == true) {
                    if (usedOldPassword.equals("") || usedNewPassword.equals("") || usedReNewPassword.equals(""))
                        Toast.makeText(ChangePasswordActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    else {
                        boolean checkOldPW = DB.checkOldPassword(usedEmail, usedOldPassword);
                        if (checkOldPW == true) {
                            if (usedNewPassword.equals(usedReNewPassword)) {
                                boolean updatePasswords = DB.updatePassword(usedEmail, usedNewPassword);
                                if (updatePasswords == true) {
                                    Toast.makeText(ChangePasswordActivity.this, "Password Change Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                                    intent.putExtra("current_user_data", currentUserDetails);  //User Details Push
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "New Password Not Matching", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Invalid Old Password", Toast.LENGTH_SHORT).show();
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
        userPhone.setText(userModel.getUsed_phone());
    }


    //Function for Validate Inputs
    private Boolean validateInputs(String usedOldPassword, String usedNewPassword, String usedReNewPassword){
        if(usedOldPassword.length()==0) {
            oldPassword.requestFocus();
            oldPassword.setError("Password Required");
            return false;
        }
        else if (!usedOldPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=_\\-!¡?¿.,;:<>])(?=\\S+$).{6,}$")) {
            oldPassword.requestFocus();
            oldPassword.setError("Use Special Characters and Numbers");
            return false;
        }
        else if(usedOldPassword.length()<=5) {
            oldPassword.requestFocus();
            oldPassword.setError("Minimum 6 Characters Required");
            return false;
        }
        else if(usedNewPassword.length()==0) {
            newPassword.requestFocus();
            newPassword.setError("Password Required");
            return false;
        }
        else if (!usedNewPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=_\\-!¡?¿.,;:<>])(?=\\S+$).{6,}$")) {
            newPassword.requestFocus();
            newPassword.setError("Use Special Characters and Numbers");
            return false;
        }
        else if(usedNewPassword.length()<=5) {
            newPassword.requestFocus();
            newPassword.setError("Minimum 6 Characters Required");
            return false;
        }
        else if(usedReNewPassword.length()==0) {
            reNewPassword.requestFocus();
            reNewPassword.setError("Password Required");
            return false;
        }
        else if (!usedReNewPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=_\\-!¡?¿.,;:<>])(?=\\S+$).{6,}$")) {
            reNewPassword.requestFocus();
            reNewPassword.setError("Use Special Characters and Numbers");
            return false;
        }
        else if(usedReNewPassword.length()<=5) {
            reNewPassword.requestFocus();
            reNewPassword.setError("Minimum 6 Characters Required");
            return false;
        }
        else {
            return true;
        }
    }
}