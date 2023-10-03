package com.capitole.hotel.entrypoint.mapper;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.entrypoint.dto.CountResponseDTO;
import com.capitole.hotel.entrypoint.dto.ReservationDTO;
import org.springframework.stereotype.Component;

@Component
public class CountResponseMapper {

    public CountResponseDTO map(final Search search){
        var reservation = search.reservation();
        var reservationDTO = new ReservationDTO(reservation.hotelId(), reservation.checkIn(), reservation.checkOut(),
                reservation.ages());
        return new CountResponseDTO(search.id(), reservationDTO, search.count());
    }
}
