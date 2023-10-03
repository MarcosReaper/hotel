package com.capitole.hotel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

public class CustomKafkaListenerErrorHandler implements KafkaListenerErrorHandler {

    Logger log = LoggerFactory.getLogger(CustomKafkaListenerErrorHandler.class);

    @Override
    public Object handleError(final Message<?> message, final ListenerExecutionFailedException exception) {
        log.error("Error processing message: {}", exception.getCause().getMessage());
        return exception.getCause();
    }
}
