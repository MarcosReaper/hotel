package com.capitole.hotel.core.repository;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.Search;

import java.util.Optional;

public interface HotelRepository {

    Search save(Search search);

    Optional<Search> findByReservation(Reservation reservation);

    Optional<Search> findById(String searchId);

}
