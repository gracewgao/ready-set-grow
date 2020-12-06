package com.example.readysetgrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn extends AppCompatActivity {
    private Button Backspace;
    private RSG_API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Backspace = (Button) findViewById(R.id.Backspace);
        Backspace.setOnClickListener(view -> openMainActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        api = retrofit.create(RSG_API.class);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openMainActivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void checkLoginInfo(View view) {
        GlobalVar.success = Boolean.FALSE;

        TextView username_text = findViewById((R.id.login_username));
        TextView password_text = findViewById((R.id.login_password));
        String username = username_text.getText().toString();
        String password = password_text.getText().toString();

        Call<List<User>> call = api.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<User> users = response.body();

                for (User u : users) {
                    if (u.getUsername().equals(username)) {
                        if (u.getPassword().equals(password)) {
                            GlobalVar.success = true;
                            GlobalVar.globalUsername = u.getUsername();
                            break;
                        }
                    }
                }

                if (GlobalVar.success) {
                    openMainActivity2();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect login information!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}