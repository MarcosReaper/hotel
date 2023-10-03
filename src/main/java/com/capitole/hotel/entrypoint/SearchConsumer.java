package com.capitole.hotel.entrypoint;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.usecase.SaveSearchUseCase;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class SearchConsumer {

    @Autowired
    SaveSearchUseCase saveSearchUseCase;

    @KafkaListener(topics = "${spring.kafka.request.topic-search}", groupId = "${spring.kafka.group.search-id}",
            errorHandler = "kafkaErrorHandler")
    @SendTo
    public String execute(SearchRequestDTO searchRequestDTO) {
        var reservation = new Reservation(searchRequestDTO.hotelId(), searchRequestDTO.checkIn(),
                searchRequestDTO.checkOut(), searchRequestDTO.ages().stream().sorted().toList());
        return saveSearchUseCase.execute(reservation);
    }
}
