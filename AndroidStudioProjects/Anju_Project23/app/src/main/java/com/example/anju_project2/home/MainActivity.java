package com.example.anju_project2.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anju_project2.R;
import com.example.anju_project2.products.ProductActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        button = findViewById(R.id.start);
        button.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ProductActivity.class)));
    }
}