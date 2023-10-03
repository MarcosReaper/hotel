package com.capitole.hotel.repository;


import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.exception.SearchNotFoundException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchIdReplySenderImplTest {

    @InjectMocks
    private SearchIdReplySenderImpl searchIdReplySenderImpl;

    @Mock
    private ReplyingKafkaTemplate<String, String, Object> replyingKafkaTemplate;

    @Mock
    private RequestReplyFuture<String, String, Object> requestReplyFuture;

    @Mock
    private ConsumerRecord<String, Object> consumerRecord;

    @Mock
    private Search search;

    @Test
    void send_searchIdReply_returnExpectedResult() throws ExecutionException, InterruptedException,
            IllegalAccessException, NoSuchFieldException {
        Field requestTopic = searchIdReplySenderImpl.getClass().getDeclaredField("requestTopic");
        requestTopic.setAccessible(true);
        requestTopic.set(searchIdReplySenderImpl, "hotel_availability_count");
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(requestReplyFuture);
        when(requestReplyFuture.get()).thenReturn(consumerRecord);
        when(consumerRecord.value()).thenReturn(search);
        when(search.id()).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        when(search.count()).thenReturn(10);

        var result = searchIdReplySenderImpl.execute("12345678-90ab-cdef-0123-456789abcdef");

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("12345678-90ab-cdef-0123-456789abcdef");
        assertThat(result.count()).isEqualTo(10);
    }

    @Test
    void send_searchIdReply_throwsException() throws ExecutionException, InterruptedException,
            IllegalAccessException, NoSuchFieldException {

        Field requestTopic = searchIdReplySenderImpl.getClass().getDeclaredField("requestTopic");
        requestTopic.setAccessible(true);
        requestTopic.set(searchIdReplySenderImpl, "hotel_availability_count");
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(requestReplyFuture);
        when(requestReplyFuture.get()).thenReturn(consumerRecord);
        when(consumerRecord.value()).thenReturn(new SearchNotFoundException("Search not found"));

        assertThatThrownBy(() -> searchIdReplySenderImpl.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .isInstanceOf(RuntimeException.class);

    }
}