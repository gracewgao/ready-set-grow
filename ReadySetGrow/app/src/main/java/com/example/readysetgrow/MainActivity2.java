package com.example.readysetgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {
    private ImageButton plant_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        plant_button = (ImageButton) findViewById(R.id.plant_button);
        plant_button.setOnClickListener(v -> openPlant());
    }

    public void openPlant(){
        Intent intent = new Intent(this, MyPlant.class);
        startActivity(intent);
    }
}