package com.example.client;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBClient {

    private static final AmazonDynamoDB instance = AmazonDynamoDBClientBuilder.defaultClient();

    public static AmazonDynamoDB getInstance() {
        return instance;
    }

}
