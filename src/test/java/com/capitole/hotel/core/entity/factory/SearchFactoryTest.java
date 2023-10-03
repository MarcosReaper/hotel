package com.capitole.hotel.core.entity.factory;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchFactoryTest {

    @InjectMocks
    private SearchFactory searchFactory;

    @Mock
    private Reservation reservation;

    @Mock
    private Search search;

    @Test
    void create_searchFactory_withReservation_returnsExpectedResult() {

        var result = searchFactory.create(reservation);
        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.count()).isEqualTo(1);
        assertThat(result.reservation()).isEqualTo(reservation);
    }

    @Test
    void create_searchFactory_withSearch_returnsExpectedResult() {
        when(search.id()).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        when(search.reservation()).thenReturn(reservation);
        when(search.count()).thenReturn(1);

        var result = searchFactory.create(search);

        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.count()).isEqualTo(2);
        assertThat(result.reservation()).isEqualTo(reservation);
    }
}