package com.example.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Client {

    private static final AmazonS3 instance = AmazonS3ClientBuilder.defaultClient();

    public static AmazonS3 getInstance() {
        return instance;
    }

}
