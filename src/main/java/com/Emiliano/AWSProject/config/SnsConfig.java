package com.Emiliano.AWSProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class SnsConfig {

    @Value("${aws.access-key-id}")
    private String accessKeyId;

    @Value("${aws.secret-access-key}")
    private String secretAccessKey;

    @Value("${aws.session-token:}")
    private String sessionToken;

    @Value("${aws.region}")
    private String region;

    @Bean
    public SnsClient snsClient() {
        StaticCredentialsProvider credentialsProvider;

        AwsSessionCredentials awsSessionCredentials = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken);
        credentialsProvider = StaticCredentialsProvider.create(awsSessionCredentials);

        return SnsClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .build();
    }
}


