package com.capitole.hotel.entrypoint;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.usecase.FindSearchUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class CountConsumer {

    @Autowired
    private FindSearchUseCase findSearchUseCase;

    @KafkaListener(topics = "${spring.kafka.request.topic-count}", groupId = "${spring.kafka.group.count-id}",
            errorHandler = "kafkaErrorHandler")
    @SendTo
    public Search execute(final String searchId) {
        return findSearchUseCase.execute(searchId);
    }
}
