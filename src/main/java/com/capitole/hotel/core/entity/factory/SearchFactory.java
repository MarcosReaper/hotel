package com.capitole.hotel.core.entity.factory;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.Search;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SearchFactory {

    public Search create(final Reservation reservation) {
        return new Search(UUID.randomUUID().toString(), reservation, 1);
    }
    public Search create(final Search search) {
        return new Search(search.id(), search.reservation(), Integer.sum(search.count(), 1));
    }
}
