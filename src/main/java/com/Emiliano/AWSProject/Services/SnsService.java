package com.Emiliano.AWSProject.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SnsService {

    @Autowired
    private SnsClient snsClient;

    @Value("${aws.arn-topic}")
    private String topicArn;

    public String publishMessage(String message, String subject) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(message)
                    .subject(subject)
                    .build();

            PublishResponse response = snsClient.publish(request);
            return response.messageId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar mensaje a SNS", e);
        }
    }
}

