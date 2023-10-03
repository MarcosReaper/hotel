package com.capitole.hotel.repository;

import com.capitole.hotel.core.repository.ReservationReplySender;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ReservationReplySenderImpl implements ReservationReplySender {

    @Value("${spring.kafka.request.topic-search}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<SearchRequestDTO, SearchRequestDTO, String> replyingKafkaTemplate;

    @Override
    public String execute(final SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException {
        ProducerRecord<SearchRequestDTO, SearchRequestDTO> producerRecord = new ProducerRecord<>(
                requestTopic, null, searchRequestDTO, searchRequestDTO);
        RequestReplyFuture<SearchRequestDTO, SearchRequestDTO, String> future = replyingKafkaTemplate
                .sendAndReceive(producerRecord);
        ConsumerRecord<SearchRequestDTO, String> response = future.get();
        return response.value();
    }


}
