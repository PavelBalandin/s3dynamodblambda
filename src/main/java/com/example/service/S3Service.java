package com.example.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.client.S3Client;

import java.io.IOException;

public class S3Service {

    private static final String BUCKET_ENV_VARIABLE = "Bucket";

    private static S3Service instance;
    private final AmazonS3 amazonS3;
    private final String bucket;


    private S3Service() {
        amazonS3 = S3Client.getInstance();
        bucket = System.getenv(BUCKET_ENV_VARIABLE);
    }

    public static S3Service getInstance() {
        if (instance == null) {
            instance = new S3Service();
        }
        return instance;
    }

    public byte[] getObject(String key) {
        S3Object s3Object = amazonS3.getObject(bucket, key);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
