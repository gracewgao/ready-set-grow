package com.example.test2;

import android.view.ViewDebug;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "Transactions")
public class Transaction {

    @DynamoDBHashKey(attributeName = "transactionId")
    private String transactionId;

    @DynamoDBAttribute(attributeName = "amount")
    private Integer amount;

    @DynamoDBAttribute(attributeName = "customer")
    @DynamoDBTypeConverted(converter = CustomerTranslator.class)
    private Customer customer;

    public void setTransactionId(String t) {
        this.transactionId = t;
    }
}
