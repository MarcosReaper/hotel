package com.capitole.hotel.core.usecase;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.repository.SearchIdReplySender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendSearchIdAndRetrieveSearchUseCaseTest {

    @InjectMocks
    private SendSearchIdAndRetrieveSearchUseCase sendSearchIdAndRetrieveSearchUseCase;

    @Mock
    private SearchIdReplySender searchIdReplySender;

    @Mock
    private Search search;

    @Test
    void execute_sendSearchIdAndRetrieveSearchUseCase_ReturnExpectedResult() throws ExecutionException,
            InterruptedException {

        when(searchIdReplySender.execute("12345678-90ab-cdef-0123-456789abcdef")).thenReturn(search);

        var result = sendSearchIdAndRetrieveSearchUseCase.execute("12345678-90ab-cdef-0123-456789abcdef");

        assertThat(result).isNotNull();
        verify(searchIdReplySender, only()).execute("12345678-90ab-cdef-0123-456789abcdef");
    }

    @Test
    void execute_sendSearchIdAndRetrieveSearchUseCase_ThrowException() throws ExecutionException,
            InterruptedException {
        when(searchIdReplySender.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .thenThrow(ExecutionException.class);

        assertThatThrownBy(() -> sendSearchIdAndRetrieveSearchUseCase
                .execute("12345678-90ab-cdef-0123-456789abcdef"));

        verify(searchIdReplySender, only()).execute("12345678-90ab-cdef-0123-456789abcdef");
    }

    @Test
    void execute_sendSearchIdAndRetrieveSearchUseCase_ThrowException2() throws ExecutionException,
            InterruptedException {

        when(searchIdReplySender.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .thenThrow(InterruptedException.class);

        assertThatThrownBy(() -> sendSearchIdAndRetrieveSearchUseCase
                .execute("12345678-90ab-cdef-0123-456789abcdef"));

        verify(searchIdReplySender, only()).execute("12345678-90ab-cdef-0123-456789abcdef");
    }

}