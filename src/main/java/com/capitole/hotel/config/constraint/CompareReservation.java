package com.capitole.hotel.config.constraint;

import com.capitole.hotel.config.constraint.validator.CompareReservationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CompareReservationValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface CompareReservation {
    String message() default "{com.capitole.hotel.config.constraint.CompareReservation.message}";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
    String checkin();
    String checkout();
}
