package com.example.readysetgrow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button SignUp;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignUp = (Button) findViewById(R.id.SignUp);
        SignUp.setOnClickListener(view -> openSignUpPage());

        Login = (Button) findViewById(R.id.Login);
        Login.setOnClickListener(view -> openLogIn());
    }

    public void openSignUpPage() {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    public void openLogIn() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}