package greencity.annotations;

import greencity.validator.StartBeforeEndTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartBeforeEndTimeValidator.class)
public @interface StartBeforeEndTime  {
    String message() default "Start time should be before end time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
