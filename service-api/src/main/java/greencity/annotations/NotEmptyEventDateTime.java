package greencity.annotations;

import greencity.validator.NotEmptyEventDateTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyEventDateTimeValidator.class)
public @interface NotEmptyEventDateTime {
    String message() default "Event must have at least one dateTime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
