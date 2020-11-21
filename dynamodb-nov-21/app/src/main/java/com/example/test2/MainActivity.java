package com.example.test2;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

// import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        ExampleThread thread = new ExampleThread();
        thread.start();
    }

    class ExampleThread extends Thread {

        @Override
        public void run() {
            DynamoDBMapper mapper;


            CognitoCachingCredentialsProvider creds;
            creds = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    "us-east-2:c8cbd0cc-0850-4dec-97f6-3d0abb947289", // Identity Pool ID
                    Regions.US_EAST_2// Region
            );

            // ProfileCredentialsProvider creds = new ProfileCredentialsProvider("default");

            AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(creds)
                    .withRegion("us-east-2")
                    .build();

            mapper = new DynamoDBMapper(ddbClient);
            DDB.save(mapper);
        }
    }
}