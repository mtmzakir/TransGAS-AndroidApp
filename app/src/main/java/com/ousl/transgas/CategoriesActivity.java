package com.ousl.transgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.ousl.transgas.adapters.GasListAdapter;
import com.ousl.transgas.model.GasModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements GasListAdapter.GasListClickListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.categories);
//        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);

        List<GasModel> gasModelList= getGasData();
        initRecyclerView(gasModelList);

        //Navigation Bottom
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.categories:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.orders:
                        startActivity(new Intent(getApplicationContext(),OrdersActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }


    // List Items
    private void initRecyclerView(List<GasModel> gasModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GasListAdapter adapter = new GasListAdapter(gasModelList,this);
        recyclerView.setAdapter(adapter);
    }

    private List<GasModel> getGasData(){
        InputStream is = getResources().openRawResource(R.raw.gas);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }
        String jsonStr = writer.toString();
        Gson gson = new Gson();
        GasModel[] gasModels = gson.fromJson(jsonStr,GasModel[].class);
        List<GasModel> gasList = Arrays.asList(gasModels);

        return  gasList;
    }

    //Back Press Home
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        overridePendingTransition(0,0);
        }

    @Override
    public void onItemClick(GasModel gasModel) {
        Intent intent = new Intent (CategoriesActivity.this, GasMenuActivity.class);
        intent.putExtra("GasModel",gasModel);
        startActivity(intent);

    }
}