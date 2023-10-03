package com.capitole.hotel.entrypoint;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.usecase.SendReservationAndRetrieveSearchUseCase;
import com.capitole.hotel.core.usecase.SendSearchIdAndRetrieveSearchUseCase;
import com.capitole.hotel.entrypoint.dto.CountResponseDTO;
import com.capitole.hotel.entrypoint.dto.ReservationDTO;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import com.capitole.hotel.entrypoint.mapper.CountResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private SendReservationAndRetrieveSearchUseCase sendReservationAndRetrieveSearchUseCase;

    @Mock
    private SendSearchIdAndRetrieveSearchUseCase sendSearchIdAndRetrieveSearchUseCase;

    @Mock
    private SearchRequestDTO searchRequestDTO;

    @Mock
    private Search search;

    @Mock
    private CountResponseMapper countResponseMapper;

    @Mock
    private CountResponseDTO countResponseDTO;

    @Mock
    private ReservationDTO reservationDTO;

    @Test
    void search_reservationController_returnsExpected() throws ExecutionException, InterruptedException {
        when(sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO))
                .thenReturn("12345678-90ab-cdef-0123-456789abcdef");

        var result = reservationController.search(searchRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().searchId()).isEqualTo("12345678-90ab-cdef-0123-456789abcdef");

        verify(sendReservationAndRetrieveSearchUseCase, only()).execute(searchRequestDTO);
    }

    @Test
    void search_reservationController_throwsException() throws ExecutionException, InterruptedException {
        when(sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO))
                .thenThrow(new ExecutionException(new RuntimeException()));

        assertThatThrownBy(() -> reservationController.search(searchRequestDTO)).isInstanceOf(ExecutionException.class);

        verify(sendReservationAndRetrieveSearchUseCase, only()).execute(searchRequestDTO);

    }

    @Test
    void search_reservationController_throwsException2() throws ExecutionException, InterruptedException {
        when(sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO))
                .thenThrow(new InterruptedException());

        assertThatThrownBy(() -> reservationController.search(searchRequestDTO))
                .isInstanceOf(InterruptedException.class);

        verify(sendReservationAndRetrieveSearchUseCase, only()).execute(searchRequestDTO);
    }

    @Test
    void count_reservationController_returnsExpected() throws ExecutionException, InterruptedException {
        when(sendSearchIdAndRetrieveSearchUseCase.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .thenReturn(search);
        when(countResponseDTO.searchId()).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        when(countResponseDTO.search()).thenReturn(reservationDTO);
        when(countResponseDTO.count()).thenReturn(1);
        when(countResponseMapper.map(search)).thenReturn(countResponseDTO);

        var result = reservationController.count("12345678-90ab-cdef-0123-456789abcdef");

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().searchId()).isEqualTo("12345678-90ab-cdef-0123-456789abcdef");
        assertThat(result.getBody().count()).isEqualTo(1);
        assertThat(result.getBody().search()).isEqualTo(reservationDTO);
        verify(sendSearchIdAndRetrieveSearchUseCase, only()).execute("12345678-90ab-cdef-0123-456789abcdef");
    }

}