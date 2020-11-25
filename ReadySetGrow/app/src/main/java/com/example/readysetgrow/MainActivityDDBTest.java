package com.example.readysetgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityDDBTest extends AppCompatActivity {
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById((R.id.a_nonexistent_id));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://8g3vuzbhi4.execute-api.us-east-2.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // thanks retrofit
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<List<Post>> call = jsonPlaceHolderAPI.getPosts();

        // thanks retrofit - auto async
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post p : posts) {
                    String content = "";
                    // i think these green strings be upper case with spaces
                    content += "title: " + p.getTitle() + "\n";
                    content += "body: " + p.getBody() + "\n";
                    content += "\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}