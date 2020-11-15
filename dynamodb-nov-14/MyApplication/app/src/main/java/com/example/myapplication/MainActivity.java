package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.DynamoDBEntry;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.gson.JsonParser;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.content.Context;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SomethingUnique su = new SomethingUnique(getApplicationContext());
/*
        // https://docs.aws.amazon.com/es_es/AWSAndroidSDK/latest/javadoc/com/amazonaws/mobileconnectors/dynamodbv2/document/datatype/Document.html
        // https://docs.aws.amazon.com/es_es/AWSAndroidSDK/latest/javadoc/com/amazonaws/mobileconnectors/dynamodbv2/document/datatype/DynamoDBEntry.html

        HashMap<String, DynamoDBEntry> m = new HashMap<String, DynamoDBEntry>();

        DynamoDBEntry entry = new Primitive
        {
            plants = "im a value";
        };
        m.put("im a key", new DynamoDBEntry("im a value"));
        su.create("ello there mate", m);
        // AmazonDynamoDBClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration
        */

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("ProductCatalog");

        List<Number> relatedItems = new ArrayList<Number>();
        relatedItems.add(341);
        relatedItems.add(472);
        relatedItems.add(649);

//Build a map of product pictures
        Map<String, String> pictures = new HashMap<String, String>();
        pictures.put("FrontView", "http://example.com/products/123_front.jpg");
        pictures.put("RearView", "http://example.com/products/123_rear.jpg");
        pictures.put("SideView", "http://example.com/products/123_left_side.jpg");

        //Build a map of product reviews
        Map<String, List<String>> reviews = new HashMap<String, List<String>>();

        List<String> fiveStarReviews = new ArrayList<String>();
        fiveStarReviews.add("Excellent! Can't recommend it highly enough!  Buy it!");
        fiveStarReviews.add("Do yourself a favor and buy this");
        reviews.put("FiveStar", fiveStarReviews);

        List<String> oneStarReviews = new ArrayList<String>();
        oneStarReviews.add("Terrible product!  Do not buy this.");
        reviews.put("OneStar", oneStarReviews);

        // Build the item
        Item item = new Item()
                .withPrimaryKey("id", "ok")
                .withString("Title", "Bicycle 123")
                .withString("Description", "123 description")
                .withString("BicycleType", "Hybrid")
                .withString("Brand", "Brand-Company C")
                .withNumber("Price", 500)
                .withStringSet("Color",  new HashSet<String>(Arrays.asList("Red", "Black")))
                .withString("ProductCategory", "Bicycle")
                .withBoolean("InStock", true)
                .withNull("QuantityOnHand")
                .withList("RelatedItems", relatedItems)
                .withMap("Pictures", pictures)
                .withMap("Reviews", reviews);

// Write the item to the table
        PutItemOutcome outcome = table.putItem(item);
    }
}