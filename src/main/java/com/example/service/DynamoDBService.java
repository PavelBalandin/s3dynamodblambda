package com.example.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.client.DynamoDBClient;

import java.util.List;

public class DynamoDBService {

    private static final String TABLE_ENV_VARIABLE = "Table";

    private static DynamoDBService instance;
    private final AmazonDynamoDB dynamoDB;
    private final String table;

    public DynamoDBService() {
        dynamoDB = DynamoDBClient.getInstance();
        table = System.getenv(TABLE_ENV_VARIABLE);
    }

    public static DynamoDBService getInstance() {
        if (instance == null) {
            instance = new DynamoDBService();
        }
        return instance;
    }

    public List<String> getImageList() {
        ScanRequest scanRequest = new ScanRequest().withTableName(table);
        ScanResult result = dynamoDB.scan(scanRequest);

        return result.getItems().stream()
                .flatMap(item -> item.values().stream())
                .map(AttributeValue::getS)
                .toList();
    }
}
