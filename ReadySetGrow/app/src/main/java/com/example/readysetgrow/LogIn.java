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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Backspace = (Button) findViewById(R.id.Backspace);
        Backspace.setOnClickListener(view -> openMainActivity());
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void checkLoginInfo(View view) {
        final boolean[] success = {false};
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        RSG_API api = retrofit.create(RSG_API.class);

        TextView username_text = findViewById((R.id.login_username));
        TextView password_text = findViewById((R.id.login_password));

        String username = username_text.getText().toString();
        String password = password_text.getText().toString();

        Call<List<User>> call = api.getUsers();

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
                        if (u.getPassword().equals(password)) {
                            success[0] = true;
                            break;
                        }
                    }
                }

                if (success[0]) {
                    // TODO: go to next activity
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect login information!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // tv.setText(t.getMessage());
            }
        });

    }
}