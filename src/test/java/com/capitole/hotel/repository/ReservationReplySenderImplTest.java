package com.capitole.hotel.repository;


import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationReplySenderImplTest {

    @InjectMocks
    private ReservationReplySenderImpl reservationReplySenderImpl;

    @Mock
    private ReplyingKafkaTemplate<SearchRequestDTO, SearchRequestDTO, String> replyingKafkaTemplate;

    @Mock
    private SearchRequestDTO searchRequestDTO;

    @Mock
    private RequestReplyFuture<SearchRequestDTO, SearchRequestDTO, String> requestReplyFuture;

    @Mock
    private ConsumerRecord<SearchRequestDTO, String> response;

    @Test
    void execute_reservationReplySender_thenReturnsExpectedResult() throws ExecutionException, InterruptedException,
            NoSuchFieldException, IllegalAccessException {

        Field requestTopic = reservationReplySenderImpl.getClass().getDeclaredField("requestTopic");
        requestTopic.setAccessible(true);
        requestTopic.set(reservationReplySenderImpl, "hotel_availability_searches");
        when(response.value()).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        when(requestReplyFuture.get()).thenReturn(response);
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(requestReplyFuture);

        var result = reservationReplySenderImpl.execute(searchRequestDTO);

        assertThat(result).isEqualTo("12345678-90ab-cdef-0123-456789abcdef");
        verify(replyingKafkaTemplate, only()).sendAndReceive(any(ProducerRecord.class));
        verify(requestReplyFuture, only()).get();
    }
}