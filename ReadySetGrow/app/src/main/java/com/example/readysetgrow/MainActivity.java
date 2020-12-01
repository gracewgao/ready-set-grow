package com.example.readysetgrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button SignUp;
    private Button Login;
    //TEMPORARY BUTTON SO THAT I CAN TEST MY CODE
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignUp = (Button) findViewById(R.id.SignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();
            }
        });

        Login = (Button) findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogIn();
            }
        });

        button = (Button) findViewById(R.id.TEST);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openSignUpPage(){
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    public void openLogIn(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}