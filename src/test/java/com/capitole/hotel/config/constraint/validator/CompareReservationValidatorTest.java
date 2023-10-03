package com.capitole.hotel.config.constraint.validator;


import com.capitole.hotel.config.constraint.CompareReservation;
import com.capitole.hotel.entrypoint.dto.SearchRequestDTO;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompareReservationValidatorTest {

    private CompareReservationValidator compareReservationValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private CompareReservation compareReservation;

    @BeforeEach
    void setUp() {
        when(compareReservation.checkin()).thenReturn("checkIn");
        when(compareReservation.checkout()).thenReturn("checkOut");
        compareReservationValidator = new CompareReservationValidator();
        compareReservationValidator.initialize(compareReservation);
    }

    @Test
    void test() {
        var checkIn = LocalDate.now();
        var checkOut = LocalDate.now().plusDays(1);
        var invalidCheckIn = LocalDate.now().minusDays(1);
        var invalidCheckOut = LocalDate.now().minusDays(2);
        var ages = List.of(1, 2, 3);
        var searchRequestDTO = new SearchRequestDTO("1", checkIn, checkOut, ages);
        var invalidSearchRequestDTO = new SearchRequestDTO("1", invalidCheckIn, invalidCheckOut, ages);

        assertThat(compareReservationValidator.isValid(null, constraintValidatorContext)).isFalse();
        assertThat(compareReservationValidator.isValid(invalidSearchRequestDTO, constraintValidatorContext)).isFalse();
        assertThat(compareReservationValidator.isValid(searchRequestDTO, constraintValidatorContext)).isTrue();
    }

}