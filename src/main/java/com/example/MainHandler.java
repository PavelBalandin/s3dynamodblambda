package com.example;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.example.service.DynamoDBService;
import com.example.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class MainHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private final S3Service s3Service = S3Service.getInstance();
    private final DynamoDBService dynamoDBService = DynamoDBService.getInstance();

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        if (input.getQueryStringParameters() == null) {
            List<String> images = dynamoDBService.getImageList();
            try {
                String body = new ObjectMapper().writeValueAsString(images);
                return APIGatewayV2HTTPResponse.builder().withStatusCode(400).withBody(body).build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        String key = input.getQueryStringParameters().get("image");

        if (key == null || key.equals("")) {
            return APIGatewayV2HTTPResponse.builder().withStatusCode(404).build();
        }

        byte[] file = s3Service.getObject(key);
        String body = Base64.getEncoder().encodeToString(file);

        return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(200)
                .withBody(body)
                .withIsBase64Encoded(true)
                .withHeaders(Collections.singletonMap("Content-Type", "image/png"))
                .build();
    }
}
