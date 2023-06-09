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
import android.widget.Button;
import android.widget.TextView;

import com.ousl.transgas.adapters.PlaceYourOrderAdapter;
import com.ousl.transgas.model.GasModel;
import com.ousl.transgas.model.Menu;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartItemsRecyclerView;
    Button placeOrderBtn;
    TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount;
    PlaceYourOrderAdapter placeYourOrderAdapter;
    String currentUserDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Parse JSON Data
        GasModel gasModel = getIntent().getParcelableExtra("GasModel");

        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        placeOrderBtn = findViewById(R.id.buttonPlaceYourOrder);
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        //Function Place Order Button
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(gasModel);
            }
        });

        initRecyclerView(gasModel);
        calculateTotalAmount(gasModel);
    }

    //Function Calculate Amount
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

    //Function Place Order Click
    private void onPlaceOrderButtonClick(GasModel gasModel) {
        //start success activity..
        Intent i = new Intent(CartActivity.this, SetDetailsActivity.class);
        i.putExtra("GasModel", gasModel);
        i.putExtra("current_user_data",currentUserDetails); //User Details Push
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

    //Function Back Press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}