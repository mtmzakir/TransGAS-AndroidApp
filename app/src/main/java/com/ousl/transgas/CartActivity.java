package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ousl.transgas.adapters.PlaceYourOrderAdapter;
import com.ousl.transgas.model.GasModel;
import com.ousl.transgas.model.Menu;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartItemsRecyclerView;
    TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    PlaceYourOrderAdapter placeYourOrderAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        GasModel gasModel = getIntent().getParcelableExtra("GasModel");

        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(gasModel);
            }
        });

        initRecyclerView(gasModel);
        calculateTotalAmount(gasModel);
    }

    private void calculateTotalAmount(GasModel gasModel) {
        float subTotalAmount = 0f;

        for(Menu m : gasModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText("Rs."+String.format("%.2f", subTotalAmount));
        tvDeliveryChargeAmount.setText("Rs."+String.format("%.2f", gasModel.getDelivery_charge()));
        subTotalAmount += gasModel.getDelivery_charge();
        tvTotalAmount.setText("Rs."+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(GasModel gasModel) {
        //start success activity..
        Intent i = new Intent(CartActivity.this, SetDetailsActivity.class);
        i.putExtra("GasModel", gasModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(GasModel gasModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(gasModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}