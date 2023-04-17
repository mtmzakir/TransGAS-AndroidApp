package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity{

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView address, viewCategory;
    ImageButton getLocation;
    private final static int REQUEST_CODE = 100;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //View All Categories
        viewCategory = findViewById(R.id.view_all);
        viewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategoryPage();
            }
        });


        //Location
        address = findViewById(R.id.address);
        getLocation = findViewById(R.id.getLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });


        //Navigation Bottom
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.categories:
                        startActivity(new Intent(getApplicationContext(),CategoriesActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
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

    //Location
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){
                                try {
                                    Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    address.setText(""+addresses.get(0).getAddressLine(0));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else {
                Toast.makeText(HomeActivity.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void openCategoryPage() {
        startActivity(new Intent(getApplicationContext(),CategoriesActivity.class));
        overridePendingTransition(0,0);
    }
}

