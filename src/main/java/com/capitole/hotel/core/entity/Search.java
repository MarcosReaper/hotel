package com.capitole.hotel.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Search (@Id String id, Reservation reservation, Integer count){

}
