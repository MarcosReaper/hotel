package com.capitole.hotel.entrypoint.dto;

public record CountResponseDTO (String searchId, ReservationDTO search, Integer count){
}
