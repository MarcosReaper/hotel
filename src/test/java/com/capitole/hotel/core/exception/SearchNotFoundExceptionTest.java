package com.capitole.hotel.core.exception;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SearchNotFoundExceptionTest {

    @Test
    void test() {
        var result = new SearchNotFoundException("Search not found with id: 1");
        assertThat(result).isNotNull();
    }

}