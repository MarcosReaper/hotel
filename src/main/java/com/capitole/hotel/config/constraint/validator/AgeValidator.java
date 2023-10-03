package com.capitole.hotel.config.constraint.validator;

import com.capitole.hotel.config.constraint.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Objects;

public class AgeValidator  implements ConstraintValidator<Age, List<Integer>> {

    @Override
    public boolean isValid(final List<Integer> ages, final ConstraintValidatorContext constraintValidatorContext) {
        return ages!=null && !ages.isEmpty() && ages.stream().filter(Objects::nonNull).allMatch(this::agesInValidRange);
    }

    private boolean agesInValidRange(Integer age) {
        return age >= 0 && age <= 120;
    }
}
