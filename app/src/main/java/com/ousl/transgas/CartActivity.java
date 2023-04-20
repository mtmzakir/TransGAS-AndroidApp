package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CartActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.cart:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.categories:
                        startActivity(new Intent(getApplicationContext(),CategoriesActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
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
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        overridePendingTransition(0,0);
    }
}