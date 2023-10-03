package com.capitole.hotel.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.time.LocalDate;
import java.util.List;

@CompoundIndex(name = "reservation", def = "{'hotelId': 1, 'checkIn': 1, 'checkOut': 1, 'ages': 1}")
public record Reservation(@Id String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {

}
