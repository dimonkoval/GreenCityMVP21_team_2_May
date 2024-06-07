package greencity.annotations;

import greencity.validator.UniqueEventDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEventDatesValidator.class)
public @interface UniqueEventDates {
    String message() default "You can't enter the same date for two days";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
