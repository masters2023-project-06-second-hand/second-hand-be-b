package com.codesquad.secondhand.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import io.findify.s3mock.S3Mock.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class S3MockConfig {

    private static final String LOCALHOST = "http://localhost:";
    private static final int S3_PORT = 8001;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Mock s3Mock() {
        S3Mock s3Mock = new Builder()
                .withPort(S3_PORT)
                .withInMemoryBackend()
                .build();
        s3Mock.start();
        return s3Mock;
    }

    @Primary
    @Bean
    @DependsOn("s3Mock")
    public AmazonS3Client amazonS3Client() {
        AwsClientBuilder.EndpointConfiguration endpoint = new EndpointConfiguration(
                LOCALHOST + S3_PORT, region);
        AmazonS3Client client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
        client.createBucket(bucket);
        return client;
    }
}
