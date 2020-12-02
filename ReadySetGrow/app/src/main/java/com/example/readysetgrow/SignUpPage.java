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
        Login2.setOnClickListener(view -> openLogIn());

        Backspace = (Button) findViewById(R.id.Backspace);
        Backspace.setOnClickListener(view -> openMainActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        api = retrofit.create(RSG_API.class);
    }

    public void openLogIn() {
        final boolean[] ret = {true};
        // TODO: check that the passwords match
        // TODO: find out what to do with the email field
        // goes to login page is registration is successful
        // should rename this function to be more representative of its functionality

        TextView username_tv = findViewById((R.id.signup_username));
        TextView password_tv = findViewById((R.id.signup_password));
        String username = username_tv.getText().toString();
        String password = password_tv.getText().toString();

        // begin checking if username is duplicate
        Call<List<User>> call = api.getUsers();
        // retrofit - auto async
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    // tv.setText("Code: " + response.code());
                    return;
                }

                List<User> users = response.body();

                for (User u : users) {
                    if (u.getUsername().equals(username)) {
                        ret[0] = false;
                        break;
                    }
                }

                if (!ret[0]) {
                    Toast.makeText(getApplicationContext(), "Username taken!", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User(username, password);
                    Call<User> call2 = api.createUser(user);

                    // TODO: toast connection error

                    call2.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (!response.isSuccessful()) {
                                // tv.setText("Code: " + response.code());
                                // return;
                            }

                            String code = response.code() + "";
                            System.out.println(code);
                        }

                        @Override
                        public void onFailure(Call<User> call2, Throwable t) {
                            // tv.setText((t.getMessage()));
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // tv.setText(t.getMessage());
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}