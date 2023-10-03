package com.capitole.hotel.config.constraint.validator;

import com.capitole.hotel.config.constraint.CompareReservation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;

public class CompareReservationValidator implements ConstraintValidator<CompareReservation, Object> {

    private String checkinFieldName;
    private String checkoutFieldName;

    @Override
    public void initialize(final CompareReservation constraintAnnotation) {
        checkinFieldName = constraintAnnotation.checkin();
        checkoutFieldName = constraintAnnotation.checkout();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            if(Objects.isNull(object)) return false;
            final Field beforeDateField = object.getClass().getDeclaredField(checkinFieldName);
            final Field afterDateField = object.getClass().getDeclaredField(checkoutFieldName);
            beforeDateField.setAccessible(true);
            afterDateField.setAccessible(true);

            final LocalDate beforeDate = (LocalDate) beforeDateField.get(object);
            final LocalDate afterDate = (LocalDate) afterDateField.get(object);
            final boolean valid = Objects.nonNull(beforeDate) && Objects.nonNull(afterDate)
                    && beforeDate.isBefore(afterDate)
                    && beforeDate.isAfter(LocalDate.now().minusDays(1))
                    && afterDate.isAfter(LocalDate.now());

            beforeDateField.setAccessible(false);
            afterDateField.setAccessible(false);

            return valid;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
