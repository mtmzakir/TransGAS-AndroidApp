package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ousl.transgas.model.UserModel;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Button logoutButton;
    TextView editProfile,changePassword;
    TextView userName,userEmail,userPhone,userAddress;
    String currentUserDetails;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        //Parse Database Data
        userName = findViewById(R.id.userNameText);
        userEmail = findViewById(R.id.userEmailText);
        userAddress = findViewById(R.id.userAddressText);
        userPhone = findViewById(R.id.userPhoneText);

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        editProfile = findViewById(R.id.editProfileButton);
        changePassword = findViewById(R.id.changePasswordButton);
        logoutButton = findViewById(R.id.logoutButton);


        //Navigation Bottom
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        //bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.profile:
                        return true;

                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.putExtra("current_user_data",currentUserDetails);  //User Details Push
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.orders:
                        Intent intent2 = new Intent(getApplicationContext(),OrdersActivity.class);
                        intent2.putExtra("current_user_data",currentUserDetails);  //User Details Push
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.categories:
                        Intent intent3 = new Intent(getApplicationContext(),CategoriesActivity.class);
                        intent3.putExtra("current_user_data",currentUserDetails);  //User Details Push
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogout();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePassword();
            }
        });

        getUserDetails();
    }

    //Function Start Login Activity
    public void openLogout() {
        Toast.makeText(ProfileActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //Function Start Edit Profile Activity
    public void openEditProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("current_user_data",currentUserDetails); //User Details Push
        startActivity(intent);
    }

    //Function Start Change Password Activity
    public void openChangePassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("current_user_data",currentUserDetails);  //UserDetails Push
        startActivity(intent);
    }

    //Function Get Current User Details
    public void getUserDetails(){
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<UserModel> aList = dbHelper.getCurrentUserDetails(currentUserDetails);
        UserModel userModel = aList.get(0);
        userName.setText(userModel.getUsed_name());
        userEmail.setText(userModel.getUsed_email());
        userPhone.setText(userModel.getUsed_phone());
        userAddress.setText(userModel.getUsed_address());
    }


    //Function Back Press
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("current_user_data",currentUserDetails);  //UserDetails Push
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}