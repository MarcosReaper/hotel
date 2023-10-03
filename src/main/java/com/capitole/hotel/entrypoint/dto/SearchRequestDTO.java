package com.capitole.hotel.entrypoint.dto;

import com.capitole.hotel.config.constraint.Age;
import com.capitole.hotel.config.constraint.CompareReservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@CompareReservation(checkin = "checkIn", checkout = "checkOut", message = "The checkIn or checkOut is not valid")
public record SearchRequestDTO(@NotBlank(message = "The hotelId is not valid")
                                    String hotelId,
                               @JsonFormat(pattern = "dd/MM/yyyy")
                                    LocalDate checkIn,
                               @JsonFormat(pattern = "dd/MM/yyyy")
                                    LocalDate checkOut,
                               @Age(message = "Ages not valid")
                                    List<Integer> ages){

}
