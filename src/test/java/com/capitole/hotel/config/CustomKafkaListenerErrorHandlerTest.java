package com.capitole.hotel.config;

import com.capitole.hotel.core.exception.SearchNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.support.GenericMessage;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CustomKafkaListenerErrorHandlerTest {

    @InjectMocks
    private CustomKafkaListenerErrorHandler customKafkaListenerErrorHandler;

    @Test
    void test() {
        var exception = new ListenerExecutionFailedException("Error processing message",
                new SearchNotFoundException("Not found"));
        var message = new GenericMessage<String>("test");
        var result = customKafkaListenerErrorHandler.handleError(message, exception);
        assertThat(result).isNotNull().isInstanceOf(SearchNotFoundException.class);
    }

}