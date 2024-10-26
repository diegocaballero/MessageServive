package org.service.message;

import io.micronaut.context.annotation.Value;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import org.service.message.dto.MessageSNS;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SnsPublisherService {

    private final SnsClient snsClient;
    private final String topicArn;
    private final ObjectMapper objectMapper;

    public SnsPublisherService(SnsClient snsClient, @Value("${aws.sns.topic-arn}") String topicArn, ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.topicArn = topicArn;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(@Valid MessageSNS message) throws IOException {
        Map<String,Object> messageContent = new HashMap<>();
        messageContent.put("Description", message.getDescription());
        messageContent.put("Date", message.getType());
        messageContent.put("Status", message.getStatus());

        String messageEvent;
        try {
            messageEvent = objectMapper.writeValueAsString(messageContent);
        } catch (IOException e) {
            throw new IOException(e);
        }

        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

        messageAttributes.put("eventType", MessageAttributeValue.builder()
                .dataType("String")
                .stringValue("MessageEvent")
                .build());

        PublishRequest publishRequest = PublishRequest.builder()
                .message(messageEvent) // body
                .topicArn(topicArn)
                .messageAttributes(messageAttributes) // additional attributes
                .build();

        PublishResponse publishResponse = snsClient.publish(publishRequest);
        return publishResponse.messageId();
    }
}
