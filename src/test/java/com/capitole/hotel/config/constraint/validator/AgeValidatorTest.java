package com.capitole.hotel.config.constraint.validator;


import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AgeValidatorTest {

    @InjectMocks
    private AgeValidator ageValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void test() {
        assertThat(ageValidator.isValid(null, constraintValidatorContext)).isFalse();
        assertThat(ageValidator.isValid(List.of(), constraintValidatorContext)).isFalse();
        assertThat(ageValidator.isValid(List.of(-10,150), constraintValidatorContext)).isFalse();
        assertThat(ageValidator.isValid(List.of(10, 20), constraintValidatorContext)).isTrue();
    }
}