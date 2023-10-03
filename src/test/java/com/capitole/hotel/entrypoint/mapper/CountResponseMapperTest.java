package com.capitole.hotel.entrypoint.mapper;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountResponseMapperTest {

    @InjectMocks
    private CountResponseMapper countResponseMapper;

    @Mock
    private Search search;

    @Mock
    private Reservation reservation;

    @Test
    void testMap() {

        when(search.id()).thenReturn("123");
        when(search.reservation()).thenReturn(reservation);
        when(reservation.hotelId()).thenReturn("456");
        when(reservation.checkIn()).thenReturn(LocalDate.now());
        when(reservation.checkOut()).thenReturn(LocalDate.now().plusDays(1));
        when(reservation.ages()).thenReturn(List.of(1, 2, 3));
        when(search.count()).thenReturn(20);

        var result = countResponseMapper.map(search);

        assertThat(result).isNotNull();
        assertThat(result.searchId()).isEqualTo("123");
        assertThat(result.search().hotelId()).isEqualTo("456");
        assertThat(result.search().checkIn()).isEqualTo(LocalDate.now());
        assertThat(result.search().checkOut()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(result.search().ages()).isEqualTo(List.of(1, 2, 3));
        assertThat(result.count()).isEqualTo(20);
    }

}