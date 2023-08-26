package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ousl.transgas.adapters.MenuListAdapter;
import com.ousl.transgas.model.GasModel;
import com.ousl.transgas.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class GasMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    List<Menu> menuList = null;
    MenuListAdapter menuListAdapter;
    List<Menu> itemsInCartList;
    int totalItemInCart = 0;
    Button CheckoutBtn;
    String currentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_menu);
//      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Parse JSON Data
        GasModel gasModel = getIntent().getParcelableExtra("GasModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(gasModel.getName() + " Menu");
        actionBar.setSubtitle("Delivery " + gasModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        menuList = gasModel.getMenus();
        initRecyclerView();

        //Function Checkout Button
        CheckoutBtn = findViewById(R.id.checkoutButton);
        CheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(GasMenuActivity.this, "Please Add Some Items", Toast.LENGTH_SHORT).show();
                    return;
                }
                gasModel.setMenus(itemsInCartList);
                Intent intent= new Intent(GasMenuActivity.this, CartActivity.class);
                intent.putExtra("GasModel", gasModel);
                intent.putExtra("current_user_data",currentUserDetails);  //UserDetails Push
                startActivityForResult(intent, 1000);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    //Function for + Button
    @Override
    public void onAddToCartClick(Menu menu) {
        if (itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;

        for (Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        CheckoutBtn.setText("Checkout (" + totalItemInCart + ") Items");
    }

    //Function for Button Update
    @Override
    public void onUpdateCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            for (Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            CheckoutBtn.setText("Checkout (" + totalItemInCart + ") Items");
        }
    }

    //Function for - Button
    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);
            totalItemInCart = 0;

            for (Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            CheckoutBtn.setText("Checkout (" + totalItemInCart + ") Items");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }
}
