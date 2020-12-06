package com.example.readysetgrow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MyPlant2 extends AppCompatActivity {
    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plant2);

        back_button = (ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(v -> openBack());
    }

    public void openBack() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}