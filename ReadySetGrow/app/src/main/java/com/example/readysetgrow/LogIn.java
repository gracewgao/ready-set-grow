package com.example.readysetgrow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    private Button Backspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Backspace = (Button) findViewById(R.id.Backspace);
        Backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void openUrl(View view){
        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://readysetgrow.auth.us-east-2.amazoncognito.com/login?response_type=code&client_id=6jv7uteqclfos8m44fneh3f1dh&redirect_uri=http://localhost:4200"));
        startActivity(openUrlIntent);
    }
}