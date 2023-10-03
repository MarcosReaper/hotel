package com.capitole.hotel.entrypoint.dto;

import java.time.LocalDate;
import java.util.List;

public record ReservationDTO (String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages){
}
