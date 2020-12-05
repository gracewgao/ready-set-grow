package com.example.readysetgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPlant extends AppCompatActivity {
    private ImageButton back_button;
    private Button water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plant);

        water = (Button) findViewById(R.id.water);
        water.setOnClickListener(v -> openNewPlant());

        back_button = (ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(v -> openBack());

        updateData();
    }

    public void openNewPlant() {
        Intent intent = new Intent(this, MyPlant2.class);
        startActivity(intent);
    }

    public void openBack() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void updateData() {
        String username = GlobalVar.globalUsername;
        final int[] temperature = {0};
        final int[] soilMoisture = {0};
        final int[] humidity = {0};
        final int[] streak = {0};
        final boolean[] success = {false};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        RSG_API api = retrofit.create(RSG_API.class);

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
                        temperature[0] = u.getTemperature();
                        soilMoisture[0] = u.getSoilMoisture();
                        humidity[0] = u.getHumidity();
                        streak[0] = u.getStreak();
                        success[0] = true;
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // tv.setText(t.getMessage());
                Toast.makeText(getApplicationContext(), "Unable to refresh.", Toast.LENGTH_LONG).show();
            }
        });

        TextView streakTV = (TextView)findViewById(R.id.textView12);
        streakTV.setText(streak[0] + " days");

        TextView temperatureTV = (TextView)findViewById(R.id.textView18);
        temperatureTV.setText(temperature[0] + " °C");

        TextView soilMoistureTV = (TextView)findViewById(R.id.textView19);
        soilMoistureTV.setText(soilMoisture[0] + "");

        TextView humidityTV = (TextView)findViewById(R.id.textView20);
        humidityTV.setText(humidity[0] + "%");
    }
}