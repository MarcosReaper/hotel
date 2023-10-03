package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.entity.factory.SearchFactory;
import com.capitole.hotel.core.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SaveSearchUseCaseTest {

    @InjectMocks
    private SaveSearchUseCase saveSearchUseCase;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private Reservation reservation;

    @Mock
    private Search search;

    @Mock
    private SearchFactory searchFactory;

    @Test
    void execute_saveSearchUseCase_whenSearchAlreadyExistInBase_ReturnExpectedResult() {
        when(search.id()).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        when(hotelRepository.findByReservation(reservation)).thenReturn(Optional.of(search));
        when(searchFactory.create(search)).thenReturn(search);
        when(hotelRepository.save(search)).thenReturn(search);

        var result = saveSearchUseCase.execute(reservation);

        assertThat(result).isNotBlank();
        verify(hotelRepository, times(1)).findByReservation(reservation);
        verify(hotelRepository, times(1)).save(search);
        verify(searchFactory, times(1)).create(search);
    }

    @Test
    void execute_saveSearchUseCase_whenSearchDoesNotExistInBase_ReturnExpectedResult() {
        when(search.id()).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        when(hotelRepository.findByReservation(reservation)).thenReturn(Optional.empty());
        when(searchFactory.create(reservation)).thenReturn(search);
        when(hotelRepository.save(search)).thenReturn(search);

        var result = saveSearchUseCase.execute(reservation);

        assertThat(result).isNotBlank();
        verify(hotelRepository, times(1)).findByReservation(reservation);
        verify(hotelRepository, times(1)).save(search);
        verify(searchFactory, times(1)).create(reservation);
    }
}