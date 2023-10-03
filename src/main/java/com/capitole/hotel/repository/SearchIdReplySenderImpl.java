package com.capitole.hotel.repository;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.repository.SearchIdReplySender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class SearchIdReplySenderImpl implements SearchIdReplySender {

    private final Logger log = LoggerFactory.getLogger(SearchIdReplySenderImpl.class);

    @Value("${spring.kafka.request.topic-count}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, String, Object> replyingKafkaTemplate;

    @Override
    public Search execute(final String searchId) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                requestTopic, null, searchId, searchId);
        RequestReplyFuture<String, String, Object> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
        ConsumerRecord<String, Object> response = future.get();
        if (response.value() instanceof RuntimeException ex){
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        return (Search) response.value();
    }
}
