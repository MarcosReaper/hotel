package com.capitole.hotel.repository;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.repository.HotelRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelMongoRepository extends HotelRepository, CrudRepository<Search, String> {

    Optional<Search> findByReservation(Reservation reservation);
}
