package com.capitole.hotel.config;

import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class HotelConfiguration {

    @Value("${spring.kafka.group.search-id}")
    private String searchGroupId;
    @Value("${spring.kafka.reply.topic-search}")
    private String searchReplyTopic;

    @Value("${spring.kafka.group.count-id}")
    private String countGroupId;
    @Value("${spring.kafka.reply.topic-count}")
    private String countReplyTopic;

    @Bean
    public ReplyingKafkaTemplate<SearchRequestDTO, SearchRequestDTO, String> replyingSearchKafkaTemplate(
            final ProducerFactory<SearchRequestDTO, SearchRequestDTO> producerFactory,
            final ConcurrentKafkaListenerContainerFactory<SearchRequestDTO, String> factory) {

        ConcurrentMessageListenerContainer<SearchRequestDTO, String> replyContainer = factory
                .createContainer(searchReplyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(searchGroupId);
        return new ReplyingKafkaTemplate<>(producerFactory, replyContainer);
    }

    @Bean
    public KafkaTemplate<SearchRequestDTO, String> replySearchTemplate(
            final ProducerFactory<SearchRequestDTO, String> producerFactory,
            final ConcurrentKafkaListenerContainerFactory<SearchRequestDTO, String> factory) {
        KafkaTemplate<SearchRequestDTO, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, Object> replyingCountKafkaTemplate(
            ProducerFactory<String, String> producerFactory,
            ConcurrentKafkaListenerContainerFactory<String, Object> factory) {

        ConcurrentMessageListenerContainer<String, Object> replyContainer = factory
                .createContainer(countReplyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(countGroupId);
        return new ReplyingKafkaTemplate<>(producerFactory, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, Object> replyCountTemplate(
            ProducerFactory<String, Object> producerFactory,
            ConcurrentKafkaListenerContainerFactory<String, Object> factory) {
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    @Bean
    public KafkaListenerErrorHandler kafkaErrorHandler() {
        return new CustomKafkaListenerErrorHandler();
    }

}
