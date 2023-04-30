package com.ousl.transgas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ousl.transgas.adapter.MenuListAdapter;
import com.ousl.transgas.model.GasModel;
import com.ousl.transgas.model.Menu;

import java.util.List;

public class GasMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    List<Menu> menuList = null;
    private MenuListAdapter menuListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_menu);

        GasModel gasModel = getIntent().getParcelableExtra("GasModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(gasModel.getName());
        actionBar.setSubtitle(gasModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        menuList = gasModel.getMenus();
        initRecyclerView();

        TextView buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public void onAddToCartClick(Menu menu) {

    }
}