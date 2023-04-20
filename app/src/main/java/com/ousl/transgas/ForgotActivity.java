package com.ousl.transgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotActivity extends AppCompatActivity {

    TextView backLoginTextButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getSupportActionBar().hide();

        backLoginTextButton = findViewById(R.id.backLoginText);
        resetButton = findViewById(R.id.resetButton);

        backLoginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgotActivity.this, "Feature Unavailable \uD83D\uDE41", Toast.LENGTH_SHORT).show();
            }
        });
    }
}