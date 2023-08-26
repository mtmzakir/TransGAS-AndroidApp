package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrdersActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String currentUserDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getSupportActionBar().hide();

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Navigation Bottom
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.orders);
        //bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.orders:
                        return true;

                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.putExtra("current_user_data",currentUserDetails);  //User Details Push
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.categories:
                        Intent intent2 = new Intent(getApplicationContext(),CategoriesActivity.class);
                        intent2.putExtra("current_user_data",currentUserDetails);  //User Details Push
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        Intent intent3 = new Intent(getApplicationContext(),ProfileActivity.class);
                        intent3.putExtra("current_user_data",currentUserDetails);  //User Details Push
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    //Back Press Home
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        intent.putExtra("current_user_data",currentUserDetails);  //User Details Push
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}