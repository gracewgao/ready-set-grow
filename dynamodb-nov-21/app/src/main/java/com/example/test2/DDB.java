package com.example.test2;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DDB {

    DynamoDBMapper mapper;

    public DDB() {
        AWSCredentialsProvider creds = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        "AKIAZ4AC4ZEBLZWBM5EL",
                        "2rnbVne9rS/FH6cqEO5/DveyKYluhVITSF4D2P0e"));

        AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(creds)
                .withRegion("us-east-2")
                .build();

        this.mapper = new DynamoDBMapper(ddbClient);
        save(mapper);
    }

    public static void load(DynamoDBMapper mapper) {
        // basic load
        Transaction t = new Transaction();
        t.setTransactionId("t1");

        Transaction result = mapper.load(t);
    }

    public static void save(DynamoDBMapper mapper) {
        Transaction t = new Transaction();
        t.setTransactionId("t4");
        t.setCustomer(Customer.builder().customerId("c2").customerName("Jane Doe").build());

        mapper.save(t);

        // mapper.batchSave(t, u, v, etc.);
    }
}
