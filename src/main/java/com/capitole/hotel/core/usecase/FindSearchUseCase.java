package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.exception.SearchNotFoundException;
import com.capitole.hotel.core.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class FindSearchUseCase {

    private final Logger log = Logger.getLogger(FindSearchUseCase.class.getName());
    public static final String SEARCH_NOT_FOUND_WITH_ID = "Search not found with id: ";
    public static final String FINDING_SEARCH_WITH_ID = "Finding search with id: ";
    @Autowired
    private HotelRepository hotelRepository;

    public Search execute(final String searchId){
        log.info(FINDING_SEARCH_WITH_ID + searchId);
        return hotelRepository.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(SEARCH_NOT_FOUND_WITH_ID + searchId));
    }
}
