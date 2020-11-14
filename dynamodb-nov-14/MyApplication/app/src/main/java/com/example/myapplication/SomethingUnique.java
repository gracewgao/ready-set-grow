package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.UpdateItemOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.List;

public class SomethingUnique {

    // private String TAG = "DynamoDb_Demo";

    // private Context context;
    private static CognitoCachingCredentialsProvider credentialsProvider;
    private static AmazonDynamoDBClient dbClient;
    private static Table dbTable;

    public SomethingUnique(Context context) {
        // Create a new credentials provider
        Regions COGNITO_IDENTITY_POOL_REGION = Regions.US_EAST_2;
        String COGNITO_IDENTITY_POOL_ID = "us-east-2:c8cbd0cc-0850-4dec-97f6-3d0abb947289";
        String DYNAMODB_TABLE = "user_info";

        credentialsProvider = new CognitoCachingCredentialsProvider(
                context, COGNITO_IDENTITY_POOL_ID, COGNITO_IDENTITY_POOL_REGION);

        // Create a connection to DynamoDB
        dbClient = new AmazonDynamoDBClient(credentialsProvider);

        // Create a table reference
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
    }

    /**
     * create a new memo in the database
     * @param memo the memo to create
     */
    public void create(Document memo) {
        if (!memo.containsKey("userId")) {
            memo.put("userId", credentialsProvider.getCachedIdentityId());
        }
        if (!memo.containsKey("noteId")) {
            //memo.put("noteId", UUID.randomUUID().toString());
            memo.put("noteId", "ajfdoijowgh");
        }
        if (!memo.containsKey("creationDate")) {
            memo.put("creationDate", System.currentTimeMillis());
        }
        dbTable.putItem(memo);
    }

    /**
     * Update an existing memo in the database
     * @param memo the memo to save
     */
    public void update(Document memo) {
        Document document = dbTable.updateItem(memo, new UpdateItemOperationConfig().withReturnValues(ReturnValue.ALL_NEW));
    }

    /**
     * Delete an existing memo in the database
     * @param memo the memo to delete
     */
    public void delete(Document memo) {
        dbTable.deleteItem(
                memo.get("userId").asPrimitive(),   // The Partition Key
                memo.get("noteId").asPrimitive());  // The Hash Key
    }

    /**
     * Retrieve a memo by noteId from the database
     * @param noteId the ID of the note
     * @return the related document
     */
    public Document getMemoById(String noteId) {
        return dbTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(noteId));
    }

    /**
     * Retrieve all the memos from the database
     * @return the list of memos
     */
    public List<Document> getAllMemos() {
        return dbTable.query(new Primitive(credentialsProvider.getCachedIdentityId())).getAllResults();
    }

}