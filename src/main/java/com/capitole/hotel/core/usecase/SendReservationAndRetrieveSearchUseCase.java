package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.repository.ReservationReplySender;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class SendReservationAndRetrieveSearchUseCase {

    @Autowired
    private ReservationReplySender reservationReplySender;

    public String execute(final SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException {
        return reservationReplySender.execute(searchRequestDTO);
    }
}
