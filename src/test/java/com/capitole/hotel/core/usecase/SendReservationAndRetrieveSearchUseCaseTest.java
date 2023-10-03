package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.repository.ReservationReplySender;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendReservationAndRetrieveSearchUseCaseTest {

    @InjectMocks
    private SendReservationAndRetrieveSearchUseCase sendReservationAndRetrieveSearchUseCase;

    @Mock
    private SearchRequestDTO searchRequestDTO;

    @Mock
    private ReservationReplySender reservationReplySender;

    @Test
    void execute_sendReservationAndRetrieveSearchUseCase_ReturnExpectedResult() throws ExecutionException,
            InterruptedException {
        when(reservationReplySender.execute(searchRequestDTO)).thenReturn("12345678-90ab-cdef-0123-456789abcdef");
        var result = sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO);

        assertThat(result).isNotBlank().isEqualTo("12345678-90ab-cdef-0123-456789abcdef");
        verify(reservationReplySender, only()).execute(searchRequestDTO);
    }

    @Test
    void execute_sendReservationAndRetrieveSearchUseCase_ThrowException() throws ExecutionException,
            InterruptedException {
        when(reservationReplySender.execute(searchRequestDTO)).thenThrow(ExecutionException.class);

        assertThatThrownBy(() -> sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO))
                .isInstanceOf(ExecutionException.class);
    }

    @Test
    void execute_sendReservationAndRetrieveSearchUseCase_ThrowException2() throws ExecutionException,
            InterruptedException {

        when(reservationReplySender.execute(searchRequestDTO)).thenThrow(InterruptedException.class);

        assertThatThrownBy(() -> sendReservationAndRetrieveSearchUseCase.execute(searchRequestDTO))
                .isInstanceOf(InterruptedException.class);
    }

}