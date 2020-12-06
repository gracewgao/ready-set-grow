package com.example.readysetgrow;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUpPage extends AppCompatActivity {
    private Button Login2;
    private Button Backspace;
    private RSG_API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Login2 = (Button) findViewById(R.id.Login2);
        Login2.setOnClickListener(view -> processRegistration());

        Backspace = (Button) findViewById(R.id.Backspace);
        Backspace.setOnClickListener(view -> openMainActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        api = retrofit.create(RSG_API.class);
    }

    public void processRegistration() {
        GlobalVar.success = Boolean.TRUE;

        TextView usernameTV = findViewById((R.id.signup_username));
        TextView passwordTV = findViewById((R.id.signup_password));
        TextView confirmTV = findViewById((R.id.signup_password_confirm));

        String username = usernameTV.getText().toString();
        String password = passwordTV.getText().toString();
        String confirm = confirmTV.getText().toString();

        // do passwords match?
        if (!password.equals(confirm)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
            return;
        }

        // is the username taken?
        Call<List<User>> call = api.getUsers();
        call.enqueue(new Callback<List<User>>() { // retrofit - auto async
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<User> users = response.body();
                for (User u : users) {
                    if (username.equals(u.getUsername())) {
                        GlobalVar.success = Boolean.FALSE;
                        break;
                    }
                }

                if (GlobalVar.success) {
                    createUser(username, password);
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Username taken!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createUser(String username, String password) {
        User user = new User(username, password);

        Call<User> call2 = api.createUser(user);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                String code = response.code() + "";
                System.out.println(">>> " + code);
            }

            @Override
            public void onFailure(Call<User> call2, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}