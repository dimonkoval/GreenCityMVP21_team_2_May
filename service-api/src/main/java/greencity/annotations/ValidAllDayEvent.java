package greencity.annotations;

import greencity.validator.ValidAllDayEventValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAllDayEventValidator.class)
public @interface ValidAllDayEvent {
    String message() default "All-day event should start at 00:00 and end at 23:59";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
