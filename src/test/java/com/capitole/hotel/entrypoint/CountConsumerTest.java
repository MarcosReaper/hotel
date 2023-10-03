package com.capitole.hotel.entrypoint;

import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.exception.SearchNotFoundException;
import com.capitole.hotel.core.usecase.FindSearchUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountConsumerTest {

    @InjectMocks
    private CountConsumer countConsumer;

    @Mock
    private FindSearchUseCase findSearchUseCase;

    @Mock
    private Search search;

    @Test
    void execute_countConsumer_returnsExpectedResult() {
        when(findSearchUseCase.execute("12345678-90ab-cdef-0123-456789abcdef")).thenReturn(search);

        var result = countConsumer.execute("12345678-90ab-cdef-0123-456789abcdef");

        assertThat(result).isNotNull().isEqualTo(search);
        verify(findSearchUseCase, only()).execute("12345678-90ab-cdef-0123-456789abcdef");
    }

    @Test
    void execute_countConsumer_returnsNull() {
        when(findSearchUseCase.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .thenThrow(SearchNotFoundException.class);

        assertThatThrownBy(() -> countConsumer.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .isInstanceOf(SearchNotFoundException.class);

        verify(findSearchUseCase, only()).execute("12345678-90ab-cdef-0123-456789abcdef");
    }
}