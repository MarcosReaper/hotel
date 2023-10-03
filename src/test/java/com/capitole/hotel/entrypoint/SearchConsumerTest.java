package com.capitole.hotel.entrypoint;


import com.capitole.hotel.core.entity.Reservation;
import com.capitole.hotel.core.usecase.SaveSearchUseCase;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchConsumerTest {

    @InjectMocks
    private SearchConsumer searchConsumer;

    @Mock
    private SearchRequestDTO searchRequestDTO;

    @Mock
    private SaveSearchUseCase saveSearchUseCase;

    @Test
    void execute_searchConsumer_returnsExpectedResult() {
        var reservation = new Reservation("1", LocalDate.now(), LocalDate.now().plusDays(1),
                List.of(1,2,3));

        when(searchRequestDTO.ages()).thenReturn(List.of(2,3,1));
        when(searchRequestDTO.checkIn()).thenReturn(LocalDate.now());
        when(searchRequestDTO.checkOut()).thenReturn(LocalDate.now().plusDays(1));
        when(searchRequestDTO.hotelId()).thenReturn("1");
        when(saveSearchUseCase.execute(eq(reservation))).thenReturn("12345678-90ab-cdef-0123-456789abcdef");

        var result = searchConsumer.execute(searchRequestDTO);

        assertThat(result).isEqualTo("12345678-90ab-cdef-0123-456789abcdef");
        verify(saveSearchUseCase, only()).execute(eq(reservation));
    }
}