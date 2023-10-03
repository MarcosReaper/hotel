package com.capitole.hotel.config.constraint;

import com.capitole.hotel.config.constraint.validator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = AgeValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface Age {
    String message() default "{com.capitole.hotel.config.constraint.Age.message}";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
