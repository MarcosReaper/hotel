package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.factory.SearchFactory;
import com.capitole.hotel.core.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SaveSearchUseCase {
    private final Logger log = Logger.getLogger(SaveSearchUseCase.class.getName());
    public static final String SAVING_SEARCH_WITH_ID = "Saving search with id: ";
    private static final String FINDING_SEARCH_WITH_RESERVATION = "Finding search with reservation on database: ";

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private SearchFactory searchFactory;

    public String execute(final Reservation reservation) {
        log.info(FINDING_SEARCH_WITH_RESERVATION + reservation.toString());
        var searchFromDB = hotelRepository.findByReservation(reservation);
        var search = searchFromDB.map(searchDB -> searchFactory.create(searchDB))
                .orElse(searchFactory.create(reservation));
        log.info(SAVING_SEARCH_WITH_ID + search.id());
        return hotelRepository.save(search).id();
    }
}
