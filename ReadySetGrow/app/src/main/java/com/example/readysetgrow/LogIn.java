package com.example.readysetgrow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    public void openUrl(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        RSG_API api = retrofit.create(RSG_API.class);

        // TODO: check if username already exists
        TextView username_text = findViewById((R.id.username_text));
        TextView password_text = findViewById((R.id.password_text));

        User user = new User(username_text.getText().toString(),
                password_text.getText().toString());
        Call<User> call = api.createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (!response.isSuccessful()) {
                    // tv.setText("Code: " + response.code());
                    return;
                }

                User userResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Username: " + userResponse.getUsername() + "\n";
                content += "Password: " + userResponse.getPassword() + "\n";
                content += "\n";

                // tv.setText(content);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // tv.setText((t.getMessage()));
            }
        });
    }
}