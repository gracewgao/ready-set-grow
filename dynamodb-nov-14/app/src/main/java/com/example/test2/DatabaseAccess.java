/*package com.example.test2;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.IDynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient; // deprecate
import com.amazonaws.client.builder.AwsClientBuilder;

public class DatabaseAccess {

    private String TAG = "DynamoDb_Demo";

    private final String COGNITO_IDENTITY_POOL_ID = "us-east-2:bddbd414-f50f-4ea0-9e96-8a963d5ecd5b";
    private final Regions COGNITO_IDENTITY_POOL_REGION = Regions.US_EAST_2;
    private final String DYNAMODB_TABLE = "user_info";
    private Context context;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDB dbClient;
    private Table table;


    private static volatile DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.context = context;

        credentialsProvider = new CognitoCachingCredentialsProvider(
                context, COGNITO_IDENTITY_POOL_ID, COGNITO_IDENTITY_POOL_REGION);

        dbClient = new AwsClientBuilder.withCredentials(credentialsProvider);
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new ProfileCredentialsProvider("default"))
                .build();

        table = new Table(client);
    }

}

/*

CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
    getApplicationContext(),
    "us-east-2:bddbd414-f50f-4ea0-9e96-8a963d5ecd5b", // Identity pool ID
    Regions.US_EAST_2 // Region
);


 */