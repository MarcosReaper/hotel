package com.capitole.hotel.core.usecase;


import com.capitole.hotel.core.entity.Search;
import com.capitole.hotel.core.exception.SearchNotFoundException;
import com.capitole.hotel.core.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindSearchUseCaseTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private FindSearchUseCase findSearchUseCase;

    @Mock
    private Search search;

    @Test
    void execute_findSearchUseCase_ReturnExpectedResult() {
        when(hotelRepository.findById("12345678-90ab-cdef-0123-456789abcdef")).thenReturn(Optional.of(search));

        var result = findSearchUseCase.execute("12345678-90ab-cdef-0123-456789abcdef");

        assertThat(result).isNotNull();
        verify(hotelRepository, only()).findById("12345678-90ab-cdef-0123-456789abcdef");
    }

    @Test
    void execute_findSearchUseCase_ThrowException() {
        when(hotelRepository.findById("12345678-90ab-cdef-0123-456789abcdef")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> findSearchUseCase.execute("12345678-90ab-cdef-0123-456789abcdef"))
                .isInstanceOf(SearchNotFoundException.class);
    }
}