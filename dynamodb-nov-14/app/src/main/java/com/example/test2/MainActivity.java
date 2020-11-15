package com.example.test2;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;


// import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        "AKIAZ4AC4ZEBLZWBM5EL",
                                        "2rnbVne9rS/FH6cqEO5/DveyKYluhVITSF4D2P0e")))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                // https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/client/builder/AwsClientBuilder.EndpointConfiguration.html
                                "https://dynamodb.us-east-2.amazonaws.com",
                                "us-east-2")).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Movies";

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                            // key
                            new KeySchemaElement("title", KeyType.RANGE)), // Sort key
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

    public void doSomething(View v) {
        /*
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-2:c8cbd0cc-0850-4dec-97f6-3d0abb947289", // Identity pool ID
                Regions.US_EAST_2 // Region
        );
        */

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        "AKIAZ4AC4ZEBLZWBM5EL",
                                        "2rnbVne9rS/FH6cqEO5/DveyKYluhVITSF4D2P0e")))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                // https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/client/builder/AwsClientBuilder.EndpointConfiguration.html
                                "https://dynamodb.us-east-2.amazonaws.com",
                                "us-east-2")).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Movies";

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                            // key
                            new KeySchemaElement("title", KeyType.RANGE)), // Sort key
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

}
/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("id").withAttributeType("N"));

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName("user_info")
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(6L));

        Table table = dynamoDB.createTable(request);

        try {
            table.waitForActive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // two

        TableDescription tableDescription =
                dynamoDB.getTable("user_info").describe();

        System.out.printf("%s: %s \t ReadCapacityUnits: %d \t WriteCapacityUnits: %d",
                tableDescription.getTableStatus(),
                tableDescription.getTableName(),
                tableDescription.getProvisionedThroughput().getReadCapacityUnits(),
                tableDescription.getProvisionedThroughput().getWriteCapacityUnits());

        // three


    }
}
*/