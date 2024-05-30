package greencity.annotations;

import greencity.validator.ValidEventDateTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEventDateTimeValidator.class)
public @interface ValidEventDateTime {
    String message() default "Event date time is incorrect. Event must start in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
