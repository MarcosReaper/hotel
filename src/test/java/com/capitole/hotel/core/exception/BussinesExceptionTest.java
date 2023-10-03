package com.capitole.hotel.core.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class BussinesExceptionTest {

    @Test
    void test() {
        var result = new BussinesException(new RuntimeException());
        assertThat(result).isNotNull();
    }

}